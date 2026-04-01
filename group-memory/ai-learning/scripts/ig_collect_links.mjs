import { chromium } from 'playwright';
import fs from 'node:fs';

const collectionUrl = process.argv[2] || 'https://www.instagram.com/';
const maxLinks = Number(process.argv[3] || 100);

const userDataDir = new URL('../.pw-profile', import.meta.url).pathname;
const outPath = new URL('../data/collection-links.json', import.meta.url).pathname;

const context = await chromium.launchPersistentContext(userDataDir, {
  headless: true,
  viewport: { width: 1365, height: 900 }
});

const page = await context.newPage();
const links = new Set();

function harvest(urls) {
  for (const u of urls) {
    if (/instagram\.com\/(reel|p)\//.test(u)) links.add(u.split('?')[0] + '/');
  }
}

try {
  await page.goto(collectionUrl, { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(2500);

  for (let i = 0; i < 35 && links.size < maxLinks; i++) {
    const urls = await page.$$eval('a[href]', as => as.map(a => a.href));
    harvest(urls);
    await page.mouse.wheel(0, 3000);
    await page.waitForTimeout(900);
  }

  const result = {
    collectedAt: new Date().toISOString(),
    source: collectionUrl,
    count: links.size,
    links: Array.from(links).slice(0, maxLinks)
  };

  fs.writeFileSync(outPath, JSON.stringify(result, null, 2));
  console.log(JSON.stringify(result, null, 2));
} finally {
  await context.close();
}
