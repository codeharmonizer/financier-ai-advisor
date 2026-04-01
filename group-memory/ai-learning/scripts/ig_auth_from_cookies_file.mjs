import fs from 'node:fs';
import { chromium } from 'playwright';

const cookiesPath = process.argv[2] || './data/ig-cookies.json';
if (!fs.existsSync(cookiesPath)) {
  console.error(`Cookies file not found: ${cookiesPath}`);
  process.exit(1);
}

let cookies;
try {
  cookies = JSON.parse(fs.readFileSync(cookiesPath, 'utf8'));
} catch (e) {
  console.error('Failed to parse cookies JSON.');
  process.exit(1);
}

if (!Array.isArray(cookies)) {
  console.error('Cookies JSON must be an array of cookie objects.');
  process.exit(1);
}

const filtered = cookies
  .filter(c => {
    const d = String(c.domain || '');
    return d.includes('instagram.com') || d.includes('.instagram.com');
  })
  .map(c => ({
    name: c.name,
    value: c.value,
    domain: c.domain?.startsWith('.') ? c.domain : `.${String(c.domain || '').replace(/^\./, '')}`,
    path: c.path || '/',
    expires: typeof c.expires === 'number' ? c.expires : (typeof c.expirationDate === 'number' ? c.expirationDate : -1),
    httpOnly: Boolean(c.httpOnly),
    secure: c.secure !== false,
    sameSite: (c.sameSite === 'Strict' || c.sameSite === 'Lax' || c.sameSite === 'None') ? c.sameSite : 'Lax'
  }))
  .filter(c => c.name && c.value && c.domain && c.path);

if (!filtered.length) {
  console.error('No valid instagram.com cookies found in file.');
  process.exit(1);
}

const userDataDir = new URL('../.pw-profile', import.meta.url).pathname;
const context = await chromium.launchPersistentContext(userDataDir, {
  headless: true,
  viewport: { width: 1365, height: 900 }
});

try {
  await context.addCookies(filtered);

  const page = await context.newPage();
  await page.goto('https://www.instagram.com/accounts/edit/', { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(3000);

  const loggedInPrivate = !page.url().includes('/accounts/login');
  if (!loggedInPrivate) {
    console.error('Cookies imported, but private auth check failed.');
    process.exit(2);
  }

  await context.storageState({ path: new URL('../.pw-storage.json', import.meta.url).pathname });
  console.log(JSON.stringify({ ok: true, imported: filtered.length, privateAuth: true }, null, 2));
} finally {
  await context.close();
}
