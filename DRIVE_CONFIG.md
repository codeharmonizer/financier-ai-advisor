# Google Drive Configuration

## Account
- **Email:** claudealtenative@gmail.com
- **Auth Method:** OAuth via gogcli
- **Keyring Password:** Stored in environment variable `GOG_KEYRING_PASSWORD`

## Project Folder Structure
- **Financier (Root):** `1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2`
  - **Requirements:** `1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae`

## Access Methods

### List Financier folder (root):
```bash
export GOG_KEYRING_PASSWORD="<passphrase>"
gog drive ls --account claudealtenative@gmail.com --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --json
```

### List Requirements folder:
```bash
export GOG_KEYRING_PASSWORD="<passphrase>"
gog drive ls --account claudealtenative@gmail.com --parent 1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae --json
```

### Get file details:
```bash
gog drive info <FILE_ID> --account claudealtenative@gmail.com --json
```

### Download file:
```bash
gog drive download <FILE_ID> --out /path/to/local --account claudealtenative@gmail.com
```

### Upload file to Financier:
```bash
gog drive upload /path/to/file --parent 1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2 --account claudealtenative@gmail.com
```

## Claude Code Integration

When Claude Code needs to access Financier files:
1. Set `GOG_KEYRING_PASSWORD` env var
2. Use `gog drive` commands with `--account claudealtenative@gmail.com`
3. Reference folder IDs as needed

### Common patterns:
- **List all project files:** Query parent folder ID
- **Search within Financier:** Use `--query "name='...'"` with parent filter
- **Bulk operations:** Combine commands in scripts/workflows
