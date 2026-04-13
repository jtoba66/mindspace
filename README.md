# <img src="mindspace_logo.png" width="80" align="center" /> MindSpace: Your Premium AI Clarity Assistant

> **A high-end, meditative space designed to transform mental clutter into structured peace.**

MindSpace is a premium Android application that leverages advanced AI to help you process "brain dumps," categorize your energy levels, and generate actionable clarity plans. Built with a focus on **Calm, Fun, and Premium** design ethics, it features fluid animations, tactile micro-interactions, and a sophisticated bento-style architecture.

---

## ✨ Key Features

- **🧠 Deep Brain Dump**: Let everything out in a serene, focused interface.
- **⚡ Energy Classification**: AI analyzes your sentiment and energy state to tailor your workspace.
- **📊 Clarity Dashboard**: High-fidelity bento cards that organize your thoughts into "Focus," "Gentle Steps," and "Wait Until Later."
- **🌅 Journey History**: A beautiful, scrollable story of your mental progress and energy shifts over time.
- **🛡️ Private Mode**: Integrated privacy settings to keep your most sensitive reflections off-device and off-history.
- **💎 Premium Bento Settings**: A sophisticated centered hero layout with high-contrast banners and tactile feedback.

---

## 🎨 Design Philosophy

MindSpace is built on the **"Candy" Design System**:
- **Squishy Haptics**: Proprietary `squishyClick` interaction model using spring-based physics for deep tactile engagement.
- **Fluid Motion**: Infinite background transition "breathing" effects and gliding navigation transitions.
- **Editorial Typography**: Tighter tracking for headlines and wider letter-spacing for labels to achieve a high-end editorial feel.
- **Default Dark Mode**: Optimized for low-light, meditative sessions with deep vibrant accents.

---

## 🛠️ Technical Stack

### **Frontend (Android)**
- **Jetpack Compose**: 100% declarative UI.
- **Spring Physics**: Custom `animateFloat`/`spring` implementations for tactile interactions.
- **Material 3**: Utilizing the latest M3 design tokens with custom typography and color mappings.
- **Shared Transitions**: Sophisticated animation logic between the input and dashboard states.

### **Backend (Express/Node.js)**
- **Node.js/Express**: Lightweight API handles processing logic.
- **AI Integration**: Custom services for classifying and structuring user reflections.

---

## 📂 Project Structure

```text
mindspace/
├── android/          # Jetpack Compose Android Application
│   ├── app/src/main/ # Core Kotlin source code
│   └── ...
├── server/           # Node.js backend services
│   ├── services/     # AI Classification logic
│   └── ...
└── artifacts/        # Design mockups, PRDs, and Philosophy docs (Internal)
```

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Koala or later)
- **Node.js** (v18+)
- **NPM** (v9+)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/jtoba66/mindspace.git
   cd mindspace
   ```

2. **Start the Backend**:
   ```bash
   cd server
   npm install
   npm run dev
   ```

3. **Run the Android App**:
   - Open the `/android` folder in Android Studio.
   - Sync Gradle.
   - Run on a physical device or emulator (API 31+ recommended).

---

## 📜 License

Internal project for private use. See `PRODUCT_PHILOSOPHY.md` for the core vision and guiding principles of MindSpace.

---

*Made with 💖 for mental clarity.*
