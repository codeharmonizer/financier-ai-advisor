import { chromium } from 'playwright';

const userDataDir = new URL('../.pw-profile', import.meta.url).pathname;
const context = await chromium.launchPersistentContext(userDataDir, {
  headless: true,
  viewport: { width: 1365, height: 900 }
});

async function check(page, url) {
  await page.goto(url, { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(2000);
  const finalUrl = page.url();
  const loggedIn = !finalUrl.includes('/accounts/login');
  return { url, finalUrl, loggedIn };
}

try {
  const page = await context.newPage();
  const home = await check(page, 'https://www.instagram.com/');
  const saved = await check(page, 'https://www.instagram.com/accounts/edit/');

  console.log(JSON.stringify({
    home,
    privateCheck: saved,
    authenticated: home.loggedIn && saved.loggedIn
  }, null, 2));
} finally {
  await context.close();
}
