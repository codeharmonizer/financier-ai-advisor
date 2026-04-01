import { chromium } from 'playwright';

const url = process.argv[2];
if (!url) {
  console.error('Usage: node scripts/ig_extract.mjs <instagram_url>');
  process.exit(1);
}

const userDataDir = new URL('../.pw-profile', import.meta.url).pathname;

const context = await chromium.launchPersistentContext(userDataDir, {
  headless: true,
  viewport: { width: 1365, height: 900 }
});

const page = await context.newPage();

const out = {
  url,
  fetchedAt: new Date().toISOString(),
  title: null,
  caption: null,
  author: null,
  isLoginRequired: false
};

try {
  await page.goto(url, { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(2500);

  const current = page.url();
  if (current.includes('/accounts/login')) {
    out.isLoginRequired = true;
  }

  const meta = await page.evaluate(() => {
    const get = (sel) => document.querySelector(sel)?.getAttribute('content') || null;
    return {
      ogTitle: get('meta[property="og:title"]'),
      ogDescription: get('meta[property="og:description"]'),
      description: get('meta[name="description"]'),
      author: document.querySelector('meta[property="instapp:owner_user_name"]')?.getAttribute('content') || null
    };
  });

  out.title = meta.ogTitle || null;
  out.author = meta.author || null;
  out.caption = meta.ogDescription || meta.description || null;

  const jsonld = await page.locator('script[type="application/ld+json"]').allTextContents().catch(() => []);
  if (!out.caption && jsonld.length) {
    for (const block of jsonld) {
      try {
        const parsed = JSON.parse(block);
        if (parsed?.caption) {
          out.caption = parsed.caption;
          break;
        }
      } catch {}
    }
  }

  console.log(JSON.stringify(out, null, 2));
} finally {
  await context.close();
}
