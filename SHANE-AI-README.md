# SHANE-AI

A personal rebranded build of the open-source **Mobile Harness** project
([techjarves/Mobile-Harness](https://github.com/techjarves/Mobile-Harness)) — an
AI-powered mobile coding environment that runs a full Ubuntu 20.04 ARM64 Linux
subsystem inside your Android phone, with no root required.

This fork changes **branding and colour only**. No agent, runtime, or feature
code was modified.

---

## What changed in this fork

| Area | Change |
|---|---|
| App name | `Mobile Harness` / `PocketDev` → **`SHANE-AI`** (all 6 locales: en, ar, es, hi, ru, zh) |
| Colour theme | Orange accent → **navy blue** (`ui/theme/Theme.kt`, `themes.xml`, 6 hardcoded panel colours) |
| Launcher icon | Orange speech bubble → **navy field with a white "S" monogram** (all vector assets) |
| Style resource | `Theme.PocketDev` → `Theme.ShaneAI` |
| In-app update | **Disabled** (see below) |
| CMake project | `mobile_harness_native` → `shane_ai_native` |
| APK filenames | `mobile-harness-*.apk` → `shane-ai-*.apk` |

### Palette

```
primary            #3B82F6   background         #0A1220
primaryContainer   #1E3A5F   surface            #111C2E
secondary          #60A5FA   surfaceVariant     #17253A
tertiary           #38BDF8   outline            #24354C
```

Light mode uses `#1D4ED8` primary on `#F5F7FB`.

---

## What was deliberately NOT changed

- **`applicationId` is still `com.jarves.mh`** — renaming it touches ~30 imports for
  no visible benefit. It only shows in Settings → Apps → App info.
- **Third-party names are intact**: Claude Code, DeepSeek Harness, Antigravity CLI,
  Anthropic, OpenRouter, Kimi, NVIDIA. These are other companies' trademarks.
- **All agent/runtime code is untouched.**

---

## ⚠️ Why in-app updates are disabled

`appUpdateManifestUrl` in `app/build.gradle.kts` is now empty by default.

Because the `applicationId` is still `com.jarves.mh`, leaving the updater pointed at
the upstream Mobile Harness release server would let a stock Mobile Harness APK
download and install over this build — **silently undoing the entire rebrand**.

`AppUpdater.check()` bails immediately on a non-HTTPS URL, so an empty value cleanly
disables the feature. To re-enable it later, point it at a manifest **you** publish:

```bash
./gradlew assembleOnlineDebug -PappUpdateManifestUrl=https://your-host/update.json
```

---

## Build it (cloud — nothing to install locally)

The build needs JDK 17, Android SDK 36, NDK 28.2.13676358 and CMake 3.22.1.
Rather than install ~15–20 GB locally, `.github/workflows/build.yml` builds it on
GitHub-hosted runners. **Public repos get these free.**

1. Create a **public** GitHub repo.
2. Push this folder to it:
   ```bash
   cd F:\OWN_AI\SHANE-AI
   git remote add origin https://github.com/<you>/<repo>.git
   git add -A
   git commit -m "SHANE-AI: navy rebrand"
   git push -u origin main
   ```
3. Open the repo → **Actions** → run `Build SHANE-AI APK`.
4. When it turns green, download the `shane-ai-apk` artifact and unzip it.

> The fork **must stay public**: the bundled PRoot is GPL-2.0 and talloc is LGPL.
> Keep the upstream MIT notice and attribution intact.

### Why `assembleOnlineDebug` and not `release`

`app/build.gradle.kts` only assigns a signing config to the `release` build type when
the `MH_UPLOAD_*` secrets are present. Without them a release APK comes out
**unsigned**, and Android refuses to install unsigned APKs. The debug build type is
auto-signed with the stock Gradle debug keystore and sideloads fine.

To ship a properly signed release, generate a keystore and add the four secrets:
`MH_UPLOAD_STORE_FILE`, `MH_UPLOAD_STORE_PASSWORD`, `MH_UPLOAD_KEY_ALIAS`,
`MH_UPLOAD_KEY_PASSWORD`, then build `assembleOnlineRelease`.

---

## Install on your phone

**Requirements:** ARM64 (`arm64-v8a`) device, **Android 9.0+**, 4 GB RAM minimum
(8 GB recommended), ~2.5 GB free storage (8 GB+ if you add the Android toolchain).

1. Enable **Install unknown apps** for whatever app you'll open the APK with
   (browser or Files).
2. Install the APK.
3. Grant notification permission and exempt SHANE-AI from battery optimisation.
4. Run the setup wizard → choose toolchains → paste your provider API key.
5. Wait for the Ubuntu rootfs (~69 MB download, ~369 MB on disk).

The app encrypts API keys with Android Keystore-backed AES-256-GCM. Nothing is
hardcoded in this repo — no API key is stored in any source file.

---

## Storage

The rebrand adds **0 bytes**. The APK and runtime are byte-for-byte the same size
as upstream.

| Item | Download | On device |
|---|---|---|
| APK | ~85 MB | ~100 MB |
| Ubuntu rootfs | 69 MB | 369 MB |
| Coding agent | 72 MB | 215 MB |
| **Baseline** | **~226 MB** | **~700 MB – 2.5 GB** |
| Android toolchain *(optional)* | 543 MB | 866 MB |
| Python stack *(optional)* | 53 MB | 42 MB |

Skip the optional toolchains unless a project needs them.

---

## Credits & licensing

Upstream: **Mobile Harness** by Tech Jarves — MIT License, Copyright © 2026.
Bundled third-party components keep their own licenses, listed in
`app/src/main/assets/licenses/`:

- PRoot — GPL-2.0
- talloc — LGPL-3.0-or-later
- libandroid-shmem — BSD-3-Clause
- Claude Code — downloaded from Anthropic's official endpoints, MIT

Claude, Claude Code, Ubuntu, Android, Kotlin, Node.js and Git are trademarks of
their respective owners. This project is not affiliated with or endorsed by
Anthropic. Mobile Harness and SHANE-AI are independent of one another.