# MindSpace / Stitch: Product Philosophy & Guardrails

This document serves as the North Star for building MindSpace. When making product, design, or engineering decisions, refer back to these core principles to ensure the app retains its "soul."

> **We are not building a tool people use. We are building a place people return to.**

## The Core Positioning
Every productivity app says "get more done." MindSpace says **"feel less crushed."** We sit in the space between notion/to-do lists (organization) and calm/headspace (meditation). The core promise is: *"Just tell me what's wrong and I'll help you think."*

People don't come back for features or AI. They come back for **a feeling they want again**: relief, clarity, and "I feel a bit better now." Every loop in this app must recreate that feeling.

---

## 1. The Transformation: Chaos → Clarity
Users come to the app with different types of "chaos":
*   **The "Everything is on Fire" Dump:** A massive list of logistical, personal, and work stressors.
*   **The "Vague Dread" Dump:** Paralyzing overwhelm without a clear task list.
*   **The "Logistical Mess" Dump:** Pure, frantic brain-dumping of chores.

### The Output (The "Clarity")
The AI must act as a filter, not just a list-maker, adapting to the user's current Energy Level.

*   **Focus on This First (The Anchor):** 1-2 items that will genuinely lower their heart rate. If Low Energy, pick the smallest task. If High Energy, tackle the biggest obstacle.
*   **Gentle Next Steps (The Momentum):** Tasks requiring < 5 minutes and zero "initiation energy" to create a quick win.
*   **Rest for Later (The Permission):** The app officially gives the user permission to stop carrying these items in their head today.

---

## 2. Handling Severe Overwhelm (The "Folding" Method)
**Rule:** The app must never look "busy."
If a user dumps a 20-step process, surfacing all 20 steps is catastrophic design. 
*   We use the **"Folding" Method**: The AI identifies all steps but *only shows the first two*. 
*   "Here is how we start. We have the whole path mapped out, but let's just look at these two for now."
*   Provide a "Deep Dive" button for when they feel ready, but keep the default view quiet and constrained.

---

## 3. Handling "Non-Task" Rants (Pure Emotion)
Users will inevitably dump feelings (frustration, grief, burnout) rather than tasks. Treating a painful emotion as a "task to be solved" feels robotic and cold.

In these cases, The Clarity Dashboard transforms into a **Validation & Reflection Page**:
*   **The Mirror (Header):** *"Tough day"* instead of "Your Plan."
*   **The Core Feeling (Bucket 1):** The AI summarizes the emotional theme to validate them. *"It sounds like you’re feeling undervalued and exhausted."*
*   **Soft Care (Bucket 2):** Offers emotional balm instead of chores. *"No tasks for today. Maybe just 5 minutes of quiet."*
*   **Saving for Later (Bucket 3):** Tucks the entry away into the Journey tab so they don't have to carry it.

> **Mantra:** If they give us **Chaos**, give them **Direction**. If they give us **Pain**, give them **Validation**.

---

## 4. The Retention Loop (Active Return)
The natural loop is: Dump → Clarity → Feel better → Leave → Life gets messy → Come back. That's passive. We make it **active** with these layers:

*   **Check-in Prompt:** After every plan, ask: *"Want me to check in on this later?"* Next day, gently: *"Hey, how did yesterday go?"* Options: "It helped" / "Let's adjust."
*   **One Small Step Follow-up:** Next day: *"Did you try the first step?"* Yes → *"That's a win."* No → *"That's okay, let's make it easier."* Builds accountability without guilt.
*   **Emotional Continuity:** Track emotional themes (overwhelmed, tired, confused) across sessions. Surface them gently on the Journey page: *"You've been working through a lot this week. Let's slow things down today."*

### Notification Philosophy (Critical)
*   **Always opt-in.** The user explicitly asks for a check-in. We never assume.
*   **Never assume emotional state.** ❌ "You seem stressed lately." ✅ "Want to reset your day?"
*   **Never guilt.** ❌ "You missed a day." ❌ Streak counters. ❌ Productivity metrics.
*   **Feel human, not app-y.** Every notification should read like a text from a calm friend.

---

## 5. Risks & Critical Guardrails

> [!WARNING]
> **The "Uncanny Valley" of Empathy**
> If the AI's tone feels templated, robotic, or fake (e.g., *"I understand your frustration!"*), it violates user trust. The system prompts must be ruthlessly refined to sound like a wise, quiet friend. It must be empathetic but understated.

> [!CAUTION]
> **Do Not Become a Therapist**
> The app must know its limits. We provide validation, not diagnosis. A friend says, "That sounds really hard." A friend does NOT say, "It sounds like you're experiencing cognitive distortions." Never advise on mental health.

> [!IMPORTANT]
> **Crisis Detection**
> We need a robust detection layer for crisis language (e.g., self-harm). If triggered, the AI must immediately route to real human resources and hotlines, bypassing standard app logic. Do not try to "soft care" a genuine crisis.

> [!TIP]
> **Speed is a Feature**
> The magic only works if it's fast. People in overwhelm have zero patience. The backend processing must be optimized to return clarity in seconds. The UI loading animation buys us ~3 seconds of goodwill. Any longer, the spell breaks.
