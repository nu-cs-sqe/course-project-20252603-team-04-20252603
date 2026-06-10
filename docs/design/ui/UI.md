# Project Brief: Emerald Estate (9x9 Board Game)

## 1. Project Overview
Emerald Estate is a modern, Monopoly-inspired digital board game designed for 2-4 players. The game features a 9x9 grid layout with a specialized sequence of properties, taxes, and chance events. The project focuses on a clean, "digital-native" aesthetic with a high-contrast emerald green branding.

## 2. Core Game Logic
### Board Layout (9x9)
The board consists of 32 tiles arranged in a square.
- **Corners:** GO, Jail, Go to Jail, Free Parking.
- **Tile Sequence (Clockwise from GO):**
    - GO
    - P 60, IRS, P 60, Chance, P 80, P 80, P 100
    - Jail
    - IRS, P 120, P 400, IRS, P 350, P 120, Chance, P 120, P 300
    - Go to Jail
    - P 200, Chance, P 180, P 180, IRS, P 160, P 160, Free
    - P 140, P 250, P 120, P 250, Chance, P 300, P 120, Chance, P 120

### Gameplay Mechanics
- **Players:** 2-4 human players.
- **Starting Conditions:** $1,000 balance per player, starting on GO.
- **Turn Flow:**
    1. Roll 2 random dice.
    2. Move player icon based on total.
    3. Execute tile action (Buy, Pay Rent, Draw Card, Pay Tax).
- **Property Actions:**
    - Buy unowned property at listed price.
    - Pay rent to owner if landed on (tiers: 1 Office, 2 Offices, Skyscraper).
    - Forced sale/resale at 80% of original price if bankrupt.
- **Chance Cards:**
    - Advance to GO, Go to Jail, Pay subscription fee ($100), Stock Market Crash (-$200 for all), AI Bubble Pop (-$500).
- **Winning Condition:** Last player remaining with a positive balance.

## 3. Visual Identity
- **Brand:** Emerald Estate
- **Color Palette:** Emerald Green (#10b981), Slate Grey, Surface Off-White.
- **Typography:** Plus Jakarta Sans (Modern, geometric sans-serif).
- **Design Tokens:** High roundness (Rounded-Full) for components and UI cards.
- **Key Assets:**
    - **Logo:** 9-square grid with a house icon.
    - **Tokens:** 3D minimalist models (Red Car, Blue Boat, Yellow Hat, Green Thimble).

## 4. Technical Specification (Target)
- **Platform:** Java Desktop Application.
- **Framework:** JFrame (Swing).
- **Architecture:** Controller-based (GameController, PropertyController, JailController, CardController).
- **Views:**
    - `MainMenuView`: Configuration and setup.
    - `BoardView`: 9x9 grid rendering.
    - `PlayerInfoView`: Sidebar with balances and ownership.
    - `CardView`: Popups for Chance and Property details.
    - `DiceView`: Interaction for rolling.

## 5. Design Assets Reference
- **Design System:** {{DATA:DESIGN_SYSTEM:DESIGN_SYSTEM_1}}
- **Main Game Board:** {{DATA:SCREEN:SCREEN_2}}
- **Card Interaction UI:** {{DATA:SCREEN:SCREEN_8}}
- **Victory Screen:** {{DATA:SCREEN:SCREEN_9}}
- **Logo:** {{DATA:IMAGE:IMAGE_3}}
- **Player Tokens:** {{DATA:IMAGE:IMAGE_6}}
