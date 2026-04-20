# 🚀 Galaxy Impact

> **An Android-based space colony management game** — recruit, train, and command a specialized crew to survive system-generated threats in a turn-based environment.

---

## 👥 Team Members

| Name | Email | Student ID |
|------|-------|------------|
| Nhat Minh Nguyen | Nhat.Minh.Nguyen@student.lut.fi | 003097696 |
| Vo Tung Quan | Quan.Vo@student.lut.fi | 003438954 |
| Do Chi Bach | Chi.Bach.Do@student.lut.fi | 003106228 |

---

## 🎯 Goal

Survive **30 days** and defeat the final boss.

---

## ⚔️ Battle Mechanics

- **Turn-based** combat system
- A coin flip at the start of each battle determines who moves first (heads = crew, tails = threat)
- Every unit can perform **1 move** per turn
- **Basic attacks** earn 1 SP; **abilities** cost SP
- Initial SP: `5` | Max SP: `10`

### Difficulty Modifiers

| Difficulty | Threat Stat Bonus |
|------------|------------------|
| Easy       | None             |
| Medium     | +15%             |
| Hard       | +30%             |
| Insane     | +50%             |

> ⚠️ Every **10 days**, a boss encounter is forced. Warnings appear on Days 9, 19, and 29.

---

## 🧑‍🚀 Crew Members

All crew level from **0 → 3**. XP required: `6 / 10 / 16` per level.

### Pilot
| Stat | Lv0 | Lv1 | Lv2 | Lv3 |
|------|-----|-----|-----|-----|
| HP   | 15  | 20  | 25  | 30  |
| ATK  | 5   | 8   | 10  | 15  |

- **Basic Attack:** Hits 1 selected + 2 random threats
- **Skill – Extra Attack** (-1 SP, 2-turn cooldown): Next attack deals an additional `20% / 25% / 30% / 40%` ATK to a random threat

### Engineer
| Stat | Lv0 | Lv1 | Lv2 | Lv3 |
|------|-----|-----|-----|-----|
| HP   | 15  | 20  | 25  | 30  |
| ATK  | 2   | 3   | 4   | 5   |

- **Basic Attack:** Hits 1 selected threat
- **Skill – Boost Defense** (-1 SP, 2-turn cooldown): Increases a crew member's max HP by 20%

### Medic
| Stat | Lv0 | Lv1 | Lv2 | Lv3 |
|------|-----|-----|-----|-----|
| HP   | 5   | 10  | 15  | 20  |
| ATK  | 1   | 2   | 3   | 4   |

- **Basic Attack:** Hits 1 selected threat
- **Skill – Heal** (-1 SP, 2-turn cooldown): Recovers `3 / 5 / 8 / 10` HP for 1 selected crew member

### Scientist
| Stat | Lv0 | Lv1 | Lv2 | Lv3 |
|------|-----|-----|-----|-----|
| HP   | 10  | 15  | 20  | 25  |
| ATK  | 2   | 3   | 4   | 5   |

- **Basic Attack:** Hits 1 selected threat
- **Skill – Boost Attack** (-1 SP, 2-turn cooldown): Increases 1 crew member's ATK by `1 / 2 / 3 / 4` for 2 turns

### Soldier
| Stat | Lv0 | Lv1 | Lv2 | Lv3 |
|------|-----|-----|-----|-----|
| HP   | 15  | 20  | 25  | 30  |
| ATK  | 4   | 6   | 8   | 10  |

- **Basic Attack:** Hits 1 selected threat
- **Skill – Enrage** (0 SP, once per battle): When HP drops below 50%, deals **10 AOE damage** to all threats and increases own ATK by `1 / 2 / 3 / 5`

---

## ⚡ Energy System

| Action        | Energy Cost | Notes         |
|---------------|-------------|---------------|
| Training      | 4           | Also heals 1 HP |
| Mission       | 5           | —             |

**Initial Energy:** `25`

---

## 🏠 Base Facilities

### 🏋️ Simulator (Training)
Select crew members to train — each gains **+2 XP**.

### 🏥 Medbay
- Queue injured crew members for recovery
- Recovers **full HP** per crew member
- Cost: **2 energy per crew**

### 🛸 Missions
- Deploy a group of **3 crew members** (5 for boss missions on Days 10, 20, 30)
- **Rewards on success:**
  - +2 Credits
  - +6 XP for each surviving crew member
- **Failure:** Each crew member who participated loses 1 level
- Crew at 0 HP are sent to the medbay queue
- **Item drops (15% chance each):**
  - 🧪 ATK Potion: +2 ATK for 3 turns (1 crew)
  - 💉 HP Potion: +3 max HP (1 crew)
  - 🛡️ DEF Potion: -1 damage taken (1 crew)

### 🧑‍💼 Recruit
Hire new crew members using credits:

| Class      | Cost    |
|------------|---------|
| Pilot      | 1 credit  |
| Medic      | 2 credits |
| Soldier    | 2 credits |
| Scientist  | 3 credits |
| Engineer   | 3 credits |

**Initial Credits:** `10`

### 🏘️ Quarters
Assign crew to missions, the simulator, or the medbay.

### 🛒 Black Market
Buy potions for **1 credit each**.

---

## 👾 Threat Stats

### Normal Threats
| Stat        | Formula |
|-------------|---------|
| HP          | `30 + (2 × total missions) + (current day / 2)` |
| ATK         | `random(6–15)` before Day 10; `random(10–18)` from Day 10 onwards |
| Resilience  | `1 + (total missions / 10)` |

### Bosses

| Boss             | Day | Bonus HP | Bonus ATK |
|------------------|-----|----------|-----------|
| Void Reaper      | 10  | +20      | +2        |
| Star Devourer    | 20  | +30      | +3        |
| The Omega Entity | 30  | +40      | +5        |

---

## 📊 Statistics

Track your colony's performance:
- Total Crew
- Total Crew XP
- Missions Run
- Total Wins
- Training Sessions
- Crew composition diagram

---

## 🔄 Other Actions

- **Finish Day** — End the current day and advance to the next
- **Restart Game** — Reset and start a new run

---

## ✨ Bonus Features

- RecycleView
- Crew Images
- Tactical Combat
- Statistics & Visualization
- No Permanent Death
- Randomness in Missions
- Larger Squads
- Data Storage & Loading
- Custom Features (potions, skill points, days, boss fights)

---

*Object-Oriented Programming Project — LUT University*
