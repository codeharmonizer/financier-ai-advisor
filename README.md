# Financier AI Advisor

An intelligent financial architecture powered by OpenClaw, Claude Code, and Google Cloud integration.

## 🎯 Overview

This project combines:
- **Real-time Gmail monitoring** via Google Pub/Sub
- **Google Drive integration** for document management
- **Claude Code** for intelligent analysis & automation
- **OpenClaw** for multi-channel orchestration

## 📦 Project Structure

```
financier-ai-advisor/
├── SOUL.md                          # Assistant personality & guidelines
├── USER.md                          # User preferences & context
├── AGENTS.md                        # Agent workspace guidelines
├── MEMORY.md                        # Long-term memory & setup details
├── IDENTITY.md                      # Assistant identity config
├── TOOLS.md                         # Local tool configurations
├── HEARTBEAT.md                     # Periodic task reminders
│
├── DRIVE_CONFIG.md                  # Google Drive setup & folder IDs
├── CLAUDE_CODE_DRIVE_INTEGRATION.md # Claude Code + Drive workflows
├── drive-access.sh                  # Helper script for Drive operations
│
├── openclaw.json                    # OpenClaw gateway config (Gmail Pub/Sub)
└── README.md                        # This file
```

## 🚀 Quick Start

### Prerequisites
- OpenClaw 2026.2+ installed
- Google Cloud project with Gmail & Drive APIs enabled
- `gog` (gogcli) for Google Cloud CLI access
- GitHub CLI (`gh`) for repo management

### Setup

1. **Gmail Monitoring (Pub/Sub)**
   ```bash
   export GOG_KEYRING_PASSWORD="<keyring-passphrase>"
   openclaw webhooks gmail setup --account claudealtenative@gmail.com --json
   ```

2. **Google Drive Access**
   ```bash
   export GOG_KEYRING_PASSWORD="<keyring-passphrase>"
   ./drive-access.sh list-financier
   ```

3. **Claude Code Integration**
   - Load `CLAUDE_CODE_DRIVE_INTEGRATION.md` for workflows
   - Use `drive-access.sh` helper script in scripts/workflows

## 📂 Google Drive Structure

```
Financier (Root)
├── ID: 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2
└── Requirements/
    └── ID: 1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae
    └── Project requirements & specifications
```

## 🔑 Key Files

| File | Purpose |
|------|---------|
| `DRIVE_CONFIG.md` | Drive folder IDs, access patterns, commands |
| `CLAUDE_CODE_DRIVE_INTEGRATION.md` | Integration examples & workflows for Claude Code |
| `drive-access.sh` | Helper script (executable) for Drive operations |
| `MEMORY.md` | Setup details, dependencies, session notes |
| `openclaw.json` | Gateway config with Gmail Pub/Sub hooks |

## 🔐 Security Notes

- **Keyring password:** Set `GOG_KEYRING_PASSWORD` env var (never commit)
- **OAuth tokens:** Stored in `~/.config/gogcli/keyring` (encrypted)
- **GitHub:** Personal access token used by `gh` CLI
- **Sensitive info:** Keep in environment variables, not in code

## 💻 Common Commands

### List Files in Financier
```bash
export GOG_KEYRING_PASSWORD="<pass>"
./drive-access.sh list-financier
```

### Download a File
```bash
./drive-access.sh download <FILE_ID> /tmp/output
```

### Search for Files
```bash
./drive-access.sh search "requirements"
```

### Using gog Directly
```bash
export GOG_KEYRING_PASSWORD="<pass>"
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json
```

## 📖 Documentation

- **OpenClaw:** https://docs.openclaw.ai
- **gogcli:** https://gogcli.sh
- **Google Cloud:** https://cloud.google.com/docs
- **Claude Code:** Integration docs in `CLAUDE_CODE_DRIVE_INTEGRATION.md`

## ⚙️ Configuration

### OpenClaw Gateway Config
See `openclaw.json` for:
- Gmail Pub/Sub watch configuration
- Webhook hooks & message templates
- Model overrides for Gmail processing
- Tailscale Funnel endpoint setup

### Drive Access
- Account: `claudealtenative@gmail.com`
- Root folder: `1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2`
- Auth method: OAuth via gogcli
- Keyring: `~/.config/gogcli/keyring` (encrypted)

## 🔄 Workflow Examples

### Email + Drive Integration
1. Gmail receives new email via Pub/Sub
2. OpenClaw hook processes email content
3. Claude Code extracts requirements
4. Files uploaded to Financier folder
5. Summary posted to delivery channel

### File Processing Pipeline
1. New files added to Financier/Requirements
2. `drive-access.sh` downloads file
3. Claude Code parses & processes
4. Results stored back in Drive or exported

## 🛠️ Troubleshooting

**`GOG_KEYRING_PASSWORD not set`**
```bash
export GOG_KEYRING_PASSWORD="<your-passphrase>"
```

**`gog not found`**
```bash
~/.local/bin/gog --help
# or
go install github.com/steipete/gogcli/cmd/gog@latest
```

**Drive commands return empty**
- Verify folder IDs are correct
- Check `claudealtenative@gmail.com` has access
- Confirm `GOG_KEYRING_PASSWORD` is set

## 📝 Session Setup

When starting a new session:
1. Read `MEMORY.md` for setup context
2. Set `export GOG_KEYRING_PASSWORD="<pass>"`
3. Verify Gmail watch: `gog gmail watch status --account claudealtenative@gmail.com`
4. Test Drive access: `./drive-access.sh list-financier`

## 🎯 Next Steps

- Add project requirements to Financier/Requirements folder
- Configure Claude Code workflows in `CLAUDE_CODE_DRIVE_INTEGRATION.md`
- Set up channel delivery in OpenClaw config
- Test end-to-end: email → analysis → Drive storage

---

**Setup Date:** 2026-04-01  
**Status:** ✅ Production Ready  
**Repository:** https://github.com/codeharmonizer/financier-ai-advisor
