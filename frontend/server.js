// server.js
const express = require('express');
const compression = require('compression');
const path = require('path');

const app = express();
const port = process.env.PORT || 8080;

// compression gzip
app.use(compression());

// servir les fichiers du build Angular
const distPath = path.join(__dirname, 'dist', 'frontend');
app.use(express.static(distPath));

// healthcheck Cloud Run
app.get('/healthz', (_req, res) => res.type('text/plain').send('ok'));

// fallback SPA Angular -> index.html
app.get('*', (_req, res) => {
  res.sendFile(path.join(distPath, 'index.html'));
});

app.listen(port, '0.0.0.0', () => {
  console.log(`[BOOT] Frontend listening on http://0.0.0.0:${port}`);
});
