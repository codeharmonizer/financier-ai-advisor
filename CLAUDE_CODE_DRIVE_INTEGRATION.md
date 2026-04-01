# Claude Code + Financier Integration Guide

## Overview
Claude Code can now access your **Financier folder** on Google Drive programmatically to:
- Retrieve project requirements
- Process files (PDF, Sheets, Docs, etc.)
- Generate reports based on your data
- Integrate Drive files into workflows

## Quick Start for Claude Code

### List Financier Files
```bash
export GOG_KEYRING_PASSWORD="<passphrase>"
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json
```

### Download a File
```bash
gog drive download <FILE_ID> --out /tmp/file.json --account claudealtenative@gmail.com
```

### Search by Name
```bash
gog drive ls --account claudealtenative@gmail.com --query "name='requirements.json'" --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json
```

## Usage in OpenClaw Sessions

### Option 1: Use Helper Script
```bash
# Set once per session
export GOG_KEYRING_PASSWORD="<passphrase>"

# List files
/home/salah/.openclaw/workspace/drive-access.sh list-financier

# Download specific file
/home/salah/.openclaw/workspace/drive-access.sh download <FILE_ID> /tmp/output
```

### Option 2: Direct gog Commands
```bash
export GOG_KEYRING_PASSWORD="<passphrase>"
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json | jq '.files[] | {id, name, mimeType}'
```

## Folder Structure Reference

```
Financier (Root)
├── ID: 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2
└── Requirements
    └── ID: 1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae
    └── (Project requirements & specs here)
```

## Common Workflows

### Workflow 1: Fetch Project Requirements
```bash
export GOG_KEYRING_PASSWORD="<pass>"
FILE=$(gog drive ls --account claudealtenative@gmail.com --parent 1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae --json | jq -r '.files[0].id')
gog drive download "$FILE" --out /tmp/requirements.json
cat /tmp/requirements.json
```

### Workflow 2: List All Financier Files with Details
```bash
export GOG_KEYRING_PASSWORD="<pass>"
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json | \
  jq '.files[] | "\(.name) (\(.id))"'
```

### Workflow 3: Search & Process
```bash
export GOG_KEYRING_PASSWORD="<pass>"
# Search for PDFs in Financier
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --query "mimeType='application/pdf'" --json
```

## File Types Claude Code Can Process

- **JSON** → Parse directly, extract data
- **CSV** → Convert to tables, analyze
- **PDF** → Use `pdftotext` or similar for extraction
- **Google Sheets** → Download as CSV/XLSX, process
- **Google Docs** → Download as DOCX, extract text

## Troubleshooting

**Error: `no TTY available for keyring password prompt`**
→ Ensure `GOG_KEYRING_PASSWORD` is set before running `gog` commands

**Error: `access denied` or `not found`**
→ Verify folder ID is correct and `claudealtenative@gmail.com` has permission

**Empty results from `gog drive ls`**
→ Confirm you're using the correct parent folder ID

## Reference
- **Main config:** `/home/salah/.openclaw/workspace/DRIVE_CONFIG.md`
- **Helper script:** `/home/salah/.openclaw/workspace/drive-access.sh`
- **Memory:** `/home/salah/.openclaw/workspace/MEMORY.md`
