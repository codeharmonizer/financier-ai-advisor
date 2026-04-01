import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const dataPath = path.join(__dirname, 'data', 'entries.json');
const indexPath = path.join(__dirname, 'index.html');
const PORT = Number(process.env.PORT || 8093);

function readDb() {
  return JSON.parse(fs.readFileSync(dataPath, 'utf8'));
}
function writeDb(db) {
  fs.writeFileSync(dataPath, JSON.stringify(db, null, 2));
}

const server = http.createServer((req, res) => {
  const url = new URL(req.url, `http://${req.headers.host}`);

  if (req.method === 'GET' && (url.pathname === '/' || url.pathname === '/index.html')) {
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(fs.readFileSync(indexPath));
  }

  if (req.method === 'GET' && url.pathname === '/api/entries') {
    const db = readDb();
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    return res.end(JSON.stringify(db));
  }

  if (req.method === 'POST' && url.pathname === '/api/update-category') {
    let body = '';
    req.on('data', chunk => (body += chunk));
    req.on('end', () => {
      try {
        const { id, subject, area } = JSON.parse(body || '{}');
        if (!id) throw new Error('Missing id');

        const db = readDb();
        const entry = (db.entries || []).find(e => e.id === id);
        if (!entry) throw new Error('Entry not found');

        if (typeof subject === 'string') entry.categorySubject = subject.trim() || 'Unclassified';
        if (typeof area === 'string') entry.categoryArea = area.trim() || 'Unclassified';
        entry.categoryManual = true;
        entry.categoryEditedAt = new Date().toISOString();

        writeDb(db);
        res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
        return res.end(JSON.stringify({ ok: true, entry }));
      } catch (e) {
        res.writeHead(400, { 'Content-Type': 'application/json; charset=utf-8' });
        return res.end(JSON.stringify({ ok: false, error: String(e.message || e) }));
      }
    });
    return;
  }

  res.writeHead(404, { 'Content-Type': 'application/json; charset=utf-8' });
  res.end(JSON.stringify({ ok: false, error: 'Not found' }));
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`AI Learning server running on :${PORT}`);
});
