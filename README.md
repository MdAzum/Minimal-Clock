# Minimal Clock

A free, ad-free, minimalist clock app for Android. One full-screen clock,
swipe left/right to change styles, everything else tucked behind a single
gear icon.

## What's included

- **The clock screen (the whole app)** — a full-screen, swipeable clock.
  Swipe left/right to move between 5 built-in styles: Minimal, Bold, Mono
  (shows seconds), Serif, and Compact (card style). Your last-viewed style
  is remembered between launches.
- **Settings (gear icon, top right)** — the only other UI in the app:
  - **Background**: White / Black / None (shows your real wallpaper) /
    Custom (pick any color with RGB sliders)
  - **About**: developer/project info — currently **dummy placeholder
    values** (see `AzumAboutInfo.kt`, one file, easy to edit later)
  - No bottom navigation, no extra screens — deliberately minimal.
- **Home-screen widgets** (added the normal Android way — long-press your
  home screen → Widgets — not promoted inside the app itself):
  - **Minimal Digital Clock** and **Minimal Analog Clock** — regular-size
    widgets.
  - **Minimal Clock Icon** — a 1×1 widget sized like an app icon, showing a
    live ticking time. See the note below on why this exists.
- No ads, no analytics SDKs, no third-party network calls.
- Package: `com.azum.clock`. Internal classes/files are prefixed `Azum`/
  `azum` for clear code ownership. The clock screen itself shows no
  branding — your name only appears in the source code and in
  Settings > About.

## About the "app icon as a live clock" request

Android does not let third-party apps make their actual launcher icon tick
in real time — that's an OS-reserved behavior used only by the system
Calendar app. The practical equivalent, and what most "clock icon" apps
actually ship, is the **Minimal Clock Icon** widget included here: a 1×1
home-screen widget sized and colored like an app icon. Long-press your home
screen → Widgets → Minimal Clock → drag "Minimal Clock Icon" into your app
grid next to (or instead of) the real app icon, and it behaves exactly like
what you described — a live ticking clock sitting where an icon would be.
Tapping the *real* app icon still opens the app straight into the big
swipeable clock.

## A note on the "No Background" (transparent) option

This uses Android's translucent-activity + show-wallpaper technique so the
clock can sit directly on your home screen wallpaper. It's a well-established
pattern, but exact behavior can vary slightly by device/launcher/Android
version — worth testing on your own phone once built.

## Requirements

- Android Studio (Koala/2024.1 or newer recommended)
- JDK 17 (bundled with recent Android Studio)
- Internet access on your machine the first time you open the project (Gradle
  needs to download dependencies — this sandbox has no internet, so the
  project has **not** been built/compiled here, only hand-written)

## How to open and run

1. Unzip the project.
2. Open Android Studio → **Open** → select the `azum-clock` folder.
3. Let Gradle sync (first sync downloads Gradle itself + dependencies, needs
   internet). If Android Studio asks to generate the Gradle wrapper jar,
   accept.
4. Run on an emulator or device (`Shift+F10` / the green ▶ button).
5. To test widgets: long-press your home screen → **Widgets** → scroll to
   **Minimal Clock** → drag any of the three onto the home screen.

## Building an APK via GitHub Actions (no Android Studio needed)

A ready-made workflow is included at `.github/workflows/build.yml`. It builds
a **debug APK** automatically using GitHub's free CI runners.

> **If a build fails:** open the failed run in the Actions tab and check the
> red step — the error message there tells you exactly what broke (missing
> dependency, compile error, etc.). Paste that message back if you want help
> diagnosing it; "the build failed" alone isn't enough to debug blind.

1. Create a new repository on GitHub (public or private, your choice).
2. Push this whole `azum-clock` folder to it:
   ```bash
   cd azum-clock
   git init
   git add .
   git commit -m "Initial Minimal Clock project"
   git branch -M main
   git remote add origin https://github.com/<your-username>/<your-repo>.git
   git push -u origin main
   ```
3. On GitHub, go to your repo's **Actions** tab. The "Build Azum Clock APK"
   workflow runs automatically after the push (or click **Run workflow** to
   trigger it manually).
4. When it finishes (green check), open that run → scroll to **Artifacts** →
   download `azum-clock-debug-apk`. Unzip it — inside is `app-debug.apk`.
5. Transfer that APK to your Android phone and install it (you'll need to
   allow "install unknown apps" for whatever app you use to open it, since
   it's not from the Play Store).

**Important:** this is a **debug APK** — fine for installing on your own
phone to test, but Android will show it as "unsigned"/untrusted to other
people, and it **cannot be uploaded to the Play Store** as-is. For a real
public release you'll eventually need to:
- Generate your own signing keystore (Android Studio: Build → Generate
  Signed App Bundle/APK, or `keytool` on the command line),
- Build a signed **release** AAB/APK with it,
- Keep that keystore file safe forever (losing it means you can never update
  the app again under the same listing).

That signing step is the part I'd recommend doing locally in Android Studio
the first time, since it involves creating and safely storing a private key —
not something to rush through in CI right away.

## Notes / next steps you may want later

- **Your real About info**: open
  `app/src/main/java/com/azum/clock/model/AzumAboutInfo.kt` and edit the
  constants — that's the only place you need to touch to update your name,
  email, website, and tagline.
- **App icon**: currently a simple vector mark (a clock face) as a placeholder
  adaptive icon. Swap `ic_launcher_foreground.xml` or replace with a PNG/SVG
  export from your own logo whenever you have final branding art.
- **More clock styles**: add new entries to the `AzumClockStyle` enum in
  `AzumClockOptions.kt` and a matching visual branch in
  `ClockPagerScreen.kt` — the swipe carousel picks them up automatically.
- **12h/24h toggle**: currently follows the device's system format
  automatically.
- **Alarm/stopwatch/world clock**: intentionally left out per your "just a
  clock" scope.
- **Publishing**: when you're ready for the Play Store, you'll sign the app
  with your own keystore under your developer account — that (plus this
  package name) is what actually secures your ownership of the app listing.
