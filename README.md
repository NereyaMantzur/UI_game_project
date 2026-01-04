# 🚀 AstroHit Game

**AstroHit** is an interactive Android arcade game where players navigate a spaceship through an asteroid field, collecting coins and avoiding collisions. The app features multiple game modes, sensor-based controls, and a location-based leaderboard.

---

## 📱 Features

### 🎮 Gameplay
* **Grid-Based Movement:** Navigate a spaceship across a 5x5 grid.
* **Obstacles & Rewards:** Avoid falling asteroids and collect coins for bonus points.
* **Lives System:** The player starts with 3 lives (hearts). The game ends when all hearts are lost.
* **Dynamic Difficulty:** The game speeds up or slows down based on user input or game events.

### 🕹️ Game Modes
1. **Slow Mode:** Relaxed gameplay with slower obstacle speed.
2. **Fast Mode:** High-speed challenge for advanced players.
3. **Sensor Mode:** Control the spaceship by tilting your device (using the accelerometer).

### 🏆 Leaderboard & Map
* **High Scores:** Top 10 scores are saved locally using `SharedPreferences`.
* **Location Integration:** Captures the player's GPS location when a high score is achieved.
* **Google Maps View:** View the location of each high score on an embedded Google Map.
* **Interactive List:** Clicking a score in the leaderboard zooms into that specific location on the map.

---

## 🛠️ Tech Stack & Libraries

* **Language:** Kotlin
* **Architecture:** MVC (Model-View-Controller)
* **UI Components:** XML Layouts, Material Design Components, Fragments.
* **Sensors:** `SensorManager` (Accelerometer) for tilt detection.
* **Location:** Google Play Services Location (`FusedLocationProviderClient`).
* **Maps:** Google Maps SDK for Android.
* **Data Persistence:** `SharedPreferences` & `Gson` for saving complex objects.
* **Image Loading:** Glide (via a custom `ImageLoader` utility).
* **Audio:** `MediaPlayer` for sound effects (crash & coin collection).

---

## 📸 Screenshots

<table>
  <tr>
    <td align="center"><b>Menu</b></td>
    <td align="center"><b>Game</b></td>
    <td align="center"><b>Scores</b></td>
  </tr>
  <tr>
    <td><img src="docs/menu_screenshot.jpg" width="100%" /></td>
    <td><img src="docs/game_screenshot.jpg" width="100%" /></td>
    <td><img src="docs/scores_screenshot.jpg" width="100%" /></td>
  </tr>
</table>

---

## 🚀 Getting Started

### Prerequisites
* Android Studio Ladybug (or newer)
* Android SDK API Level 24+ (Min SDK)
* A Google Maps API Key

### Installation

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/NereyaMantzur/AstroHit_Game.git](https://github.com/NereyaMantzur/AstroHit_Game.git)
