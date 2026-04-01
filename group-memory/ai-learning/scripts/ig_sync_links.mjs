import fs from 'node:fs';
import path from 'node:path';
import { execFileSync } from 'node:child_process';

const base = path.resolve(path.dirname(new URL(import.meta.url).pathname), '..');
const linksPath = path.join(base, 'data', 'collection-links.json');
const dbPath = path.join(base, 'data', 'entries.json');

if (!fs.existsSync(linksPath)) {
  console.error('Missing data/collection-links.json');
  process.exit(1);
}

const links = JSON.parse(fs.readFileSync(linksPath, 'utf8')).links || [];
const db = JSON.parse(fs.readFileSync(dbPath, 'utf8'));
const existing = new Set((db.entries || []).map(e => (e.sourceUrl || '').split('?')[0]));

let added = 0, skipped = 0, failed = 0;
for (const url of links) {
  const norm = url.split('?')[0];
  if (existing.has(norm)) { skipped++; continue; }
  try {
    const raw = execFileSync('node', [path.join(base, 'scripts', 'ig_extract.mjs'), url], { encoding: 'utf8' });
    const ex = JSON.parse(raw);

    if (!ex.title || !ex.caption) { failed++; continue; }

    const entry = {
      id: `ig-${Date.now()}-${Math.random().toString(36).slice(2,7)}`,
      type: 'instagram',
      sourceUrl: url,
      fetchedAt: ex.fetchedAt,
      author: ex.author || null,
      title: ex.title,
      caption: ex.caption,
      summary: null,
      whyItMatters: null,
      actions: [],
      tags: []
    };

    db.entries.unshift(entry);
    existing.add(norm);
    added++;
  } catch {
    failed++;
  }
}

fs.writeFileSync(dbPath, JSON.stringify(db, null, 2));
console.log(JSON.stringify({ added, skipped, failed, note: 'New entries added as drafts (not displayed until completed).' }, null, 2));
