import fs from 'node:fs';
import path from 'node:path';
import { execFileSync } from 'node:child_process';

const url = process.argv[2];
if (!url) {
  console.error('Usage: node scripts/ig_add.mjs <instagram_url>');
  process.exit(1);
}

const base = path.resolve(path.dirname(new URL(import.meta.url).pathname), '..');
const dataPath = path.join(base, 'data', 'entries.json');

const raw = execFileSync('node', [path.join(base, 'scripts', 'ig_extract.mjs'), url], { encoding: 'utf8' });
const extracted = JSON.parse(raw);

if (extracted.isLoginRequired || !extracted.caption || !extracted.title) {
  console.error(JSON.stringify({
    ok: false,
    reason: 'INSUFFICIENT_DATA',
    details: extracted
  }, null, 2));
  process.exit(2);
}

const db = JSON.parse(fs.readFileSync(dataPath, 'utf8'));

const entry = {
  id: `ig-${Date.now()}`,
  type: 'instagram',
  sourceUrl: url,
  fetchedAt: extracted.fetchedAt,
  author: extracted.author,
  title: extracted.title,
  caption: extracted.caption,
  summary: null,
  whyItMatters: null,
  actions: [],
  tags: []
};

db.entries.unshift(entry);
fs.writeFileSync(dataPath, JSON.stringify(db, null, 2));

console.log(JSON.stringify({ ok: true, entry }, null, 2));
