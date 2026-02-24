# OneTask – Engo Onboarding Demo

A demo Android application showcasing a polished onboarding flow for the **Engo** English learning app. Built with modern Android development best practices.

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Kaandemirrs/OneTask.git
   ```
2. Open the project in **Android Studio** (Hedgehog or newer recommended)
3. Sync Gradle dependencies
4. Run on an emulator or physical device (API 24+)

## Architecture

**MVVM + Jetpack Compose + Compose Navigation + Hilt DI**

- **MVVM**: Shared `OnboardingViewModel` manages state across all onboarding screens
- **Jetpack Compose**: Fully declarative UI with custom Canvas-based charts and animations
- **Navigation**: Compose Navigation with typed destinations (`AppDestination` sealed class)
- **DI**: Hilt for dependency injection
- **Persistence**: SharedPreferences for onboarding completion state and locale preference

## What Was Implemented

### 4 Onboarding Screens

1. **Skill Selection** – Choose which English skills to focus on (pronunciation, grammar, etc.)
2. **Goal Setting** – Pick a daily practice goal (5/10/15/30 min) with an animated gradient line chart
3. **Community Reviews** – Social proof cards with staggered layout and gradient styling
4. **Topic Selection** – Grid of topic cards with images (Restaurant, Business, Travel, etc.)

### Key Features

- **Custom Animated Chart** – Canvas-based curved gradient line (orange→yellow→green) with 3 dashed fan-out projection lines, spring animations on goal selection
- **Localization** – Full English 🇬🇧 & Spanish 🇪🇸 support with runtime language toggle on the Home screen
- **Onboarding Persistence** – Completed onboarding state saved via SharedPreferences; skips onboarding on relaunch
- **Reusable Components** – `PrimaryGradientButton`, `OnboardingProgressBar`, `SelectableSkillItem`, `SelectableTopicCard`, `ReviewItem`
- **Premium UI** – Gradient buttons, rounded cards, smooth spring animations, staggered card layouts

### Navigation Flow

```
Home → Skill Selection → Goal Setting → Reviews → Topics → Home (onboarding complete)
```

## Assumptions & Tradeoffs

- Used `SharedPreferences` (via `LocaleHelper`) for locale persistence – lightweight and sufficient for this scope
- Review avatars and topic images are bundled as drawable resources
- The animated chart uses quadratic Bézier curves for smooth rendering without external libraries
- `Activity.recreate()` is used for locale switching to ensure all screens reflect the new language immediately

## Project Structure

```
app/src/main/java/com/example/taskone/
├── MainActivity.kt
├── ui/
│   ├── components/          # Reusable UI components
│   ├── navigation/          # AppNavHost, Destinations
│   ├── screens/
│   │   ├── home/            # HomeScreen with language toggle
│   │   └── onboarding/      # 4 onboarding screens + ViewModel
│   └── theme/               # Material3 theme
├── util/
│   └── LocaleHelper.kt     # Runtime locale switching
└── res/
    ├── values/strings.xml        # English strings
    └── values-es/strings.xml     # Spanish strings
```
