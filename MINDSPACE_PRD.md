# MindSpace — Product Requirements Document

> **We are not building a tool people use. We are building a place people return to.**

## 1. Product Overview

| Field | Value |
|---|---|
| **App Name** | MindSpace |
| **Platform** | Android (Native) |
| **Language** | Kotlin + Jetpack Compose |
| **Design System** | Material 3 / Material You |
| **Backend** | Node.js + TypeScript (local during dev) |
| **Database** | SQLite (local on dev machine, migrated for production) |
| **Target Audience** | Women experiencing overwhelm, stress, or mental clutter |
| **Monetization** | Free (monetization strategy TBD before production) |
| **Authentication** | None in v1 (added before production launch) |

### What It Is
A "Thought → Clarity" companion app for moments when everything feels overwhelming. The user opens the app, dumps whatever is on their mind (text or voice), and gets back gentle, structured clarity — not a to-do list, but a smaller world to live in for the rest of the day.

### What It Is NOT
- ❌ A productivity tool or task manager
- ❌ A therapy app or mental health diagnostic
- ❌ A journaling app with metrics and streaks
- ❌ An MVP — this is a full, polished product

---

## 2. Core User Flow

```
[Open App]
    ↓
[Welcome Back] ← returning user (acknowledges previous plan, archives to Journey)
    ↓
[Brain Dump] ← user types or speaks freely
    ↓
    ├── [Overwhelmed?] → [Crisis Support] → Breathe / Talk / Defer
    ↓
[Energy Check-in] ← Low / Steady / Ready
    ↓
[Processing] ← 3-layer AI pipeline runs
    ↓
[Clarity Dashboard] ← plan OR validation, depending on input type
    ↓
[Check-in prompt] ← "Want me to check in on this later?"
    ↓
[User leaves]
    ↓
[Next day notification] ← "How did yesterday go?"
    ↓
[Return → Welcome Back → new cycle]
```

### First-Time User Flow
New users skip Welcome Back and land on an onboarding screen: *"You don't need to have it together to start. Just open up."* → Brain Dump.

---

## 3. AI Architecture (3 Layers + Safety)

### 🔴 Layer 0: Safety Layer (Non-Negotiable, Runs First)

| Field | Detail |
|---|---|
| **Trigger** | Crisis signals, self-harm language, harmful intent |
| **Action** | Bypass all normal AI flow immediately |
| **Output** | Supportive message + real crisis resources (hotlines, text lines) |
| **Implementation** | Keyword matching + lightweight classifier |
| **Fallback** | If detection is uncertain, err on the side of showing resources |

> [!CAUTION]
> This layer runs BEFORE any other processing. It is not optional. It protects users, the product, and legal liability.

---

### 🟡 Layer 1: Input Classification (Light Intelligence)

| Field | Detail |
|---|---|
| **Purpose** | Understand the user before responding |
| **Model** | Rules + keywords first, then Gemini Flash / GPT-4.1 mini |
| **Cost** | Cheap |

**What it detects:**

| Signal | Values | How It's Used |
|---|---|---|
| **Intent** | Planning / Emotional / Mixed | Determines dashboard format |
| **Energy** | Overwhelmed / Tired / Confused / Steady / Ready | Adjusts plan complexity |
| **Volume** | Few items / Many items | Triggers "Folding" method |
| **Continuity** | New session / Continuation | Passes previous context |

**Output:** A structured classification object passed to Layer 2:
```json
{
  "intent": "mixed",
  "energy": "low",
  "volume": "high",
  "risk_level": "none",
  "tone": "gentle",
  "response_format": "plan_with_validation",
  "previous_context": "Tuesday session about work stress"
}
```

---

### 🔵 Layer 2: Core Response (The Magic)

| Field | Detail |
|---|---|
| **Purpose** | Generate the actual human response |
| **Model** | Claude Sonnet |
| **Cost** | Primary cost center |

**Receives from Layer 1:**
- Detected state, desired tone, response format, previous context

**Generates:**
- The core content: validation, focus items, gentle steps, rest-for-later items
- Adapts voice based on intent (Direction for chaos, Validation for pain)
- Incorporates energy level (Low → fewer/smaller steps, High → bigger challenges)

**Tone Guidelines (baked into system prompt):**
- Wise friend, not therapist
- Understated empathy, not performative
- Short sentences, warm language
- Never clinical, never diagnostic
- Written for women who are exhausted and need softness, not a lecture

---

### 🟢 Layer 3: Output Formatting (Structure + Polish)

| Field | Detail |
|---|---|
| **Purpose** | Make responses app-ready and consistent |
| **Model** | Cheap model OR deterministic formatting |
| **Cost** | Minimal |

**What it does:**
- Formats into consistent sections (What's going on / What matters / One small step)
- Enforces length limits (no walls of text)
- Enforces tone boundaries (catches anything that slipped through)
- Returns structured JSON the Android app can render directly

**Output structure:**
```json
{
  "type": "plan",
  "header": "Let's focus on what matters",
  "sections": [
    {
      "title": "Focus on This First",
      "icon": "target",
      "items": ["Finish the slide structure — just the outline, not the whole thing"]
    },
    {
      "title": "Gentle Next Steps",
      "icon": "leaf",
      "items": ["Text Sarah: 'Working on it'", "Set a 5-min timer to clear the sink"]
    },
    {
      "title": "Rest for Later",
      "icon": "moon",
      "items": ["Call Mom — Saturday is fine", "Groceries — tomorrow morning"]
    }
  ],
  "closing": "You don't have to do it all. Just start with one."
}
```

---

## 4. Features (Full Specification)

### 4.1 Brain Dump (Input)
- **Text input:** Full-screen, minimal textarea with floating label feel
- **Voice input:** Google Chirp 3 — speech-to-text, transcription shown in real-time
- **"Overwhelmed?" escape:** Visible shortcut to Crisis Support if user can't type
- **No word limit:** Accept anything from 5 words to 2000
- **Private Mode toggle:** 🔒 "Don't save this" — processes but does not store

### 4.2 Energy Check-in
- **Three options:** Low / Steady / Ready
- **Visual differentiation:** Each has distinct icon, color, and description
- **Passed to AI:** Energy level directly shapes plan complexity

### 4.3 Clarity Dashboard (Output)
- **Plan mode:** 3 buckets: Focus / Gentle Steps / Rest for Later
- **Validation mode:** Mirror + Soft Care + Archive (for emotional rants)
- **Folding:** If many steps, show only first 2 with "Show full path" option
- **Check-in prompt:** Bottom of dashboard: "Want me to check in on this later?"
- **Persistent:** Accessible anytime via Organize tab

### 4.4 Crisis Support
- **Entry:** "Overwhelmed?" button on Brain Dump OR Safety Layer trigger
- **Options:** Breathe with me (guided) / Just Talk (voice) / Come back later (defer)
- **Resources:** Real hotline numbers always visible (988 Suicide & Crisis Lifeline)

### 4.5 Welcome Back (Returning Users)
- **Previous plan summary:** Shows what the last session was about
- **Archive confirmation:** "We'll keep it safe in your Journey"
- **Options:** "Start Fresh" / "Review old plan first"

### 4.6 Journey (History)
- **Timeline:** Journal-style entries from past sessions
- **Emotional themes:** "Current Theme: Finding Balance"
- **Offline:** ✅ Fully available without internet

---

## 5. Data & Privacy Model
- **Storage:** Default stores brain dumps + plans in SQLite.
- **Private Mode:** Toggle 🔒 "Don't save this" — processed but not stored.
- **User Control:** Delete any single entry or full wipe one tap.
- **Messaging:** "We remember what helps you move forward. You can turn this off anytime 💖"

---

## 6. Offline Capabilities
- ✅ View past plans / history
- ✅ View Journey timeline
- ❌ New Brain Dump / Voice Input (requires AI/Internet)

---

## 7. Technical Architecture
- **Android App:** Kotlin + Jetpack Compose + Material 3
- **Local Cache:** Room DB (SQLite)
- **Backend:** Node.js + TS
- **AI Pipeline:** Safety → Classification → Claude CORE → Output Polish

---

## 8. Design Principles
- Soft, not clinical (warm pinks, rounded corners)
- Quiet, not busy (max 2 focus items visible)
- Permission, not pressure (no streaks)
- Human, not robotic (wise friend tone)

---

## 9. Success Metrics
- Return rate (Day 1 / Day 7)
- Check-in opt-in rate
- Crisis detection accuracy (Recall >99%)
- NO tracking of: streaks, daily live time, or productivity scores.
