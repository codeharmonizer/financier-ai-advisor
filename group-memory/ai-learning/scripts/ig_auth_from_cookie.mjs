import { chromium } from 'playwright';

const sessionId = process.env.IG_SESSIONID;
if (!sessionId) {
  console.error('Missing IG_SESSIONID env var');
  process.exit(1);
}

const userDataDir = new URL('../.pw-profile', import.meta.url).pathname;
const context = await chromium.launchPersistentContext(userDataDir, {
  headless: true,
  viewport: { width: 1365, height: 900 }
});

try {
  await context.addCookies([
    {
      name: 'sessionid',
      value: sessionId,
      domain: '.instagram.com',
      path: '/',
      httpOnly: true,
      secure: true,
      sameSite: 'None'
    }
  ]);

  const page = await context.newPage();
  await page.goto('https://www.instagram.com/', { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(3000);

  const isLoggedIn = !page.url().includes('/accounts/login');
  if (!isLoggedIn) {
    console.error('Session cookie did not authenticate.');
    process.exit(2);
  }

  await context.storageState({ path: new URL('../.pw-storage.json', import.meta.url).pathname });
  console.log('OK_AUTHENTICATED');
} finally {
  await context.close();
}
