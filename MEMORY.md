# MEMORY.md - Long-Term Memory

## 2026-04-01: Gmail + Google Drive Setup Complete

### Gmail (Pub/Sub Watch)
- **Account:** `claudealtenative@gmail.com`
- **GCP Project:** `financier-finsync`
- **Status:** ✅ Active & watching INBOX
- **Watch expires:** 2026-04-08 (auto-renews weekly)
- **OpenClaw config:** `hooks.gmail` configured in `openclaw.json`
- **Public endpoint:** `https://vps-a21714c1.tail2da881.ts.net/gmail-pubsub` (via Tailscale Funnel)

**What this means:**
- OpenClaw receives new emails in real-time
- Emails are processed as hooks and can trigger agent workflows
- Gmail history tracked since 2026-04-01T21:43:04Z

### Google Drive (API Access)
- **Account:** `claudealtenative@gmail.com`
- **Root Project Folder:** `Financier` (ID: `1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2`)
- **Subfolders:** Requirements (ID: `1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae`)
- **Auth method:** OAuth via `gogcli` (gog CLI)
- **Keyring password:** Set via `GOG_KEYRING_PASSWORD` env var
- **Scope:** Full Drive access (drive service enabled)

**Helper scripts:**
- `drive-access.sh` — wrapper for common Drive operations
- `DRIVE_CONFIG.md` — detailed Drive configuration reference

**Claude Code Access:**
Claude Code can call `gog drive` commands directly. Common patterns:
```bash
export GOG_KEYRING_PASSWORD="<passphrase>"
gog drive ls --account claudealtenative@gmail.com --parent <FOLDER_ID> --json
gog drive download <FILE_ID> --out /path
gog drive upload /path --parent <FOLDER_ID>
```

### Project Files Location
User will add project requirements/files to the Financier folder. I have full programmatic access to list, download, and process these files on demand.

### Key Dependencies
- `gog` (gogcli) — installed at `~/.local/bin/gog`
- `gcloud` — Google Cloud CLI (installed via apt)
- `tailscale` — VPN/tunnel (already available)
- OpenClaw webhooks configured for Gmail

### Future Sessions
When starting a new session:
1. Load this MEMORY.md to recall the setup
2. Use `drive-access.sh` for Drive operations
3. Set `GOG_KEYRING_PASSWORD` when running Drive/Gmail commands
4. Check Financier folder for new project files

---

**Setup Date:** 2026-04-01  
**Status:** ✅ Complete and tested
