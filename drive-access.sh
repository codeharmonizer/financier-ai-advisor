#!/bin/bash
# drive-access.sh - Helper script for Financier folder access

set -e

ACCOUNT="claudealtenative@gmail.com"
FINANCIER_ID="1dXwMX1BdcMDfAnGDJ8Y_fohwMMgSY7t2"
REQUIREMENTS_ID="1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae"

# Check if passphrase is set
if [ -z "$GOG_KEYRING_PASSWORD" ]; then
  echo "Error: GOG_KEYRING_PASSWORD env var not set"
  exit 1
fi

# Command dispatcher
case "$1" in
  list-financier)
    echo "📁 Listing Financier folder..."
    gog drive ls --account "$ACCOUNT" --parent "$FINANCIER_ID" --json
    ;;
  
  list-requirements)
    echo "📁 Listing Requirements folder..."
    gog drive ls --account "$ACCOUNT" --parent "$REQUIREMENTS_ID" --json
    ;;
  
  list-all)
    echo "📁 Listing all Financier contents recursively..."
    # Recursively list all folders and files
    gog drive ls --account "$ACCOUNT" --parent "$FINANCIER_ID" --json | jq -r '.files[] | .id + " " + .name'
    ;;
  
  info)
    if [ -z "$2" ]; then
      echo "Usage: $0 info <FILE_ID>"
      exit 1
    fi
    echo "ℹ️  File info for $2..."
    gog drive info "$2" --account "$ACCOUNT" --json
    ;;
  
  download)
    if [ -z "$2" ] || [ -z "$3" ]; then
      echo "Usage: $0 download <FILE_ID> <OUTPUT_PATH>"
      exit 1
    fi
    echo "⬇️  Downloading $2 to $3..."
    gog drive download "$2" --out "$3" --account "$ACCOUNT"
    ;;
  
  upload)
    if [ -z "$2" ] || [ -z "$3" ]; then
      echo "Usage: $0 upload <LOCAL_FILE> <PARENT_FOLDER_ID>"
      exit 1
    fi
    echo "⬆️  Uploading $2 to folder $3..."
    gog drive upload "$2" --parent "$3" --account "$ACCOUNT"
    ;;
  
  search)
    if [ -z "$2" ]; then
      echo "Usage: $0 search <QUERY>"
      exit 1
    fi
    echo "🔍 Searching for: $2"
    gog drive ls --account "$ACCOUNT" --query "name contains '$2'" --json
    ;;
  
  *)
    cat << 'EOF'
Usage: drive-access.sh <command> [args]

Commands:
  list-financier         List files in Financier root
  list-requirements      List files in Requirements subfolder
  list-all              List all Financier contents
  info <FILE_ID>        Get file metadata
  download <ID> <PATH>  Download file
  upload <FILE> <PARENT_ID>  Upload file to folder
  search <QUERY>        Search by filename

Examples:
  ./drive-access.sh list-financier
  ./drive-access.sh download 1JzCj3JOsCOeNmJQTOPIT_ePPAWdicUae ~/requirements.json
  ./drive-access.sh search "invoice"
EOF
    exit 1
    ;;
esac
