#!/usr/bin/env python3
"""
CurseForge upload script for Minecraft mods using RetroFuturaGradle/CleanroomMC template.

Reads all configuration from gradle.properties automatically.
Designed to be copy-pasted between mod projects.

Usage:
    # Upload the latest built jar (reads all config from gradle.properties)
    python scripts/upload_curseforge.py

    # Upload a specific jar
    python scripts/upload_curseforge.py --file build/libs/MyMod-1.0.0.jar

    # Dry run (only print what would be uploaded)
    python scripts/upload_curseforge.py --dry-run

Requires:
    - curl_cffi library (pip install curl_cffi)
    - CURSEFORGE_TOKEN environment variable set
"""

import argparse
import json
import os
import re
import sys
from pathlib import Path

from curl_cffi import requests, CurlMime
import tempfile


# --- Configuration ---
# API endpoint
API_ENDPOINT = "https://legacy.curseforge.com/api"

# Game version IDs for Minecraft 1.12.2, Forge, Java 8
# These are the string names (the API also accepts names via gameVersionNames)
GAME_VERSIONS = ["1.12.2", "Forge", "Java 8"]
ENVIRONMENTS = ["Client", "Server"]

# Paths
SCRIPT_DIR = Path(__file__).resolve().parent
PROJECT_DIR = SCRIPT_DIR.parent
GRADLE_PROPERTIES = PROJECT_DIR / "gradle.properties"
CHANGELOG_FILE = PROJECT_DIR / "CHANGELOG.md"
BUILD_LIBS_DIR = PROJECT_DIR / "build" / "libs"

def upload_to_curseforge(
    api_token: str,
    project_id: str,
    file_path: Path,
    release_type: str,
    changelog: str,
    changelog_type: str = "markdown",
    display_name: str = None,
    dry_run: bool = False,
):
    """Upload a file to CurseForge using custom headers (bypasses Cloudflare check)."""
    metadata = {
        "releaseType": release_type,
        "gameVersionNames": GAME_VERSIONS + ENVIRONMENTS,
        "changelog": changelog,
        "changelogType": changelog_type,
    }
    if display_name:
        metadata["displayName"] = display_name

    if dry_run:
        print("=== DRY RUN ===")
        print(f"Project ID: {project_id}")
        print(f"File: {file_path}")
        print(f"File size: {file_path.stat().st_size} bytes")
        print(f"Release type: {release_type}")
        print(f"Game versions: {GAME_VERSIONS + ENVIRONMENTS}")
        print(f"Display name: {display_name}")
        print(f"Changelog ({changelog_type}):")
        print("-" * 40)
        print(changelog if changelog else "(empty)")
        print("-" * 40)
        print("Metadata JSON:")
        print(json.dumps(metadata, indent=2))
        print("=== END DRY RUN ===")
        return True

    url = f"{API_ENDPOINT}/projects/{project_id}/upload-file"
    metadata_json = json.dumps(metadata)

    print(f"Uploading to: {url}")
    print(f"File: {file_path.name} ({file_path.stat().st_size / 1024:.1f} KB)")

    # curl_cffi requires CurlMime object for multipart uploads
    with open(file_path, "rb") as f:
        file_data = f.read()
    mp = CurlMime()
    mp.addpart(
        name="metadata",
        content_type="application/json",
        data=metadata_json.encode(),
    )
    mp.addpart(
        name="file",
        content_type="application/java-archive",
        filename=file_path.name,
        data=file_data,
    )
    headers = {
        "X-Api-Token": api_token,
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36",
    }
    response = requests.post(url, headers=headers, multipart=mp, impersonate="chrome")
    mp.close()

    if response.status_code == 200:
        result = response.json()
        file_id = result.get("id", "unknown")
        print(f"Upload successful! File ID: {file_id}")
        return True
    else:
        print(f"Upload failed (HTTP {response.status_code}):")
        try:
            error = response.json()
            print(json.dumps(error, indent=2))
        except json.JSONDecodeError:
            print(response.text[:2000])
        return False


def main():
    parser = argparse.ArgumentParser(description="Upload the mod to CurseForge")
    parser.add_argument(
        "--file", "-f",
        type=str,
        help="Path to the jar file to upload",
    )
    parser.add_argument(
        "--project-id",
        type=str,
        default=None,
        help="CurseForge project ID",
    )
    parser.add_argument(
        "--release-type",
        type=str,
        choices=["alpha", "beta", "release"],
        default=None,
        help="Release type",
    )
    parser.add_argument(
        "--display_name",type=str, default=None, help="Display name for the upload"
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Print what would be uploaded without actually uploading",
    )
    parser.add_argument(
        "--dev",
        action="store_true",
        help="Upload the -dev.jar variant",
    )
    parser.add_argument(
        "--api_token",type=str, default=None, help="Changelog text",
    )
    parser.add_argument(
        "--changelog",type=str, default=None, help="Changelog text",
    )
    args = parser.parse_args()

    # Get API token from environment
    api_token:str = args.api_token or os.environ.get("CURSEFORGE_TOKEN")
    if not api_token and not args.dry_run:
        print("Error: CURSEFORGE_TOKEN environment variable is not set!")
        print("Set it with: export CURSEFORGE_TOKEN='your_token_here'")
        sys.exit(1)

    file_path:str = args.file
    release_type:str = args.release_type
    project_id:str = args.project_id
    display_name:str = args.display_name
    changelog:str = args.changelog

    print(f"Mod File: {file_path}, Display Name: {display_name}")
    print(f"CurseForge Project ID: {project_id}")
    print(f"Release type: {release_type}")

    # Find the jar file
    jar_path = Path(args.file)
    if not jar_path.exists():
        print(f"Error: File not found: {jar_path}")
        sys.exit(1)

    # Get changelog
    changelog = args.changelog

    # Upload
    success = upload_to_curseforge(
        api_token=api_token,
        project_id=project_id,
        file_path=jar_path,
        release_type=release_type,
        changelog=changelog,
        changelog_type="markdown",
        display_name=display_name,
        dry_run=args.dry_run,
    )

    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()
