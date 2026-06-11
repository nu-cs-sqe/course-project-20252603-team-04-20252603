---
name: Emerald Estate
colors:
  surface: '#f4fbf4'
  surface-dim: '#d4dcd5'
  surface-bright: '#f4fbf4'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eef6ee'
  surface-container: '#e8f0e9'
  surface-container-high: '#e3eae3'
  surface-container-highest: '#dde4dd'
  on-surface: '#161d19'
  on-surface-variant: '#3c4a42'
  inverse-surface: '#2b322d'
  inverse-on-surface: '#ebf3eb'
  outline: '#6c7a71'
  outline-variant: '#bbcabf'
  surface-tint: '#006c49'
  primary: '#006c49'
  on-primary: '#ffffff'
  primary-container: '#10b981'
  on-primary-container: '#00422b'
  inverse-primary: '#4edea3'
  secondary: '#5c5f61'
  on-secondary: '#ffffff'
  secondary-container: '#e0e3e5'
  on-secondary-container: '#626567'
  tertiary: '#545f73'
  on-tertiary: '#ffffff'
  tertiary-container: '#98a3ba'
  on-tertiary-container: '#2e394c'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#6ffbbe'
  primary-fixed-dim: '#4edea3'
  on-primary-fixed: '#002113'
  on-primary-fixed-variant: '#005236'
  secondary-fixed: '#e0e3e5'
  secondary-fixed-dim: '#c4c7c9'
  on-secondary-fixed: '#191c1e'
  on-secondary-fixed-variant: '#444749'
  tertiary-fixed: '#d8e3fb'
  tertiary-fixed-dim: '#bcc7de'
  on-tertiary-fixed: '#111c2d'
  on-tertiary-fixed-variant: '#3c475a'
  background: '#f4fbf4'
  on-background: '#161d19'
  surface-variant: '#dde4dd'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 48px
    fontWeight: '800'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: '1.3'
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: '1.2'
  body-stats:
    fontFamily: Manrope
    fontSize: 18px
    fontWeight: '600'
    lineHeight: '1.5'
    letterSpacing: 0.02em
  body-main:
    fontFamily: Manrope
    fontSize: 16px
    fontWeight: '500'
    lineHeight: '1.6'
  label-caps:
    fontFamily: Manrope
    fontSize: 12px
    fontWeight: '700'
    lineHeight: '1'
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Manrope
    fontSize: 12px
    fontWeight: '600'
    lineHeight: '1'
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  unit: 8px
  margin-sm: 16px
  margin-md: 32px
  margin-lg: 48px
  gutter: 24px
  board-tile-gap: 4px
---

## Brand & Style

This design system reimagines the classic tabletop property-trading experience for a modern digital landscape. The brand personality is professional and structured yet retains a playful, approachable spirit. It targets a digital-native audience that values clarity, speed, and visual polish.

The aesthetic follows a **Modern / Corporate** style with a focus on **Flat Design** principles. Depth is achieved not through heavy textures, but through subtle layering and soft ambient shadows. The goal is to reduce the cognitive load of a complex board game state by using high-contrast typography and a clean, organized interface that feels like a premium SaaS product but functions like a high-stakes game.

## Colors

The palette is anchored by **Emerald Green**, used primarily for the game board environment to evoke the traditional felt or wooden surface of classic board games. **Crisp White** is reserved for high-interaction surfaces like cards, menus, and modal dialogs to ensure maximum readability.

**Deep Slate** serves as the primary typographic color, providing a softer, more professional alternative to pure black. Accent colors are highly saturated to distinguish player tokens, property groups, and critical game alerts:
- **Crimson Red**: Player 1 / Danger / High-Value Properties.
- **Cobalt Blue**: Player 2 / Utility / Information.
- **Amber Gold**: Player 3 / Opportunity / Rewards.
- **Forest Green**: Player 4 / Stability / Growth.

## Typography

The typography system balances personality with utility. **Plus Jakarta Sans** is used for headlines to provide a friendly, rounded geometric feel that softens the "cutthroat" nature of the game. It is set with tight letter spacing for a modern, punchy look.

**Manrope** is the workhorse for game statistics and body text. Its high x-height and open apertures ensure that numerical values (cash, rents, mortgage rates) are legible even at smaller sizes or within dense data grids. Game stats should utilize the `Medium` or `SemiBold` weights by default to maintain visibility against colored backgrounds.

## Layout & Spacing

The layout employs a **Fluid Grid** for menus and overlays, while the game board utilizes a **Fixed Aspect Ratio** container to maintain the integrity of the square tiles across devices. 

- **Desktop**: 12-column grid with a center-focused board and side-docked player inventories.
- **Tablet**: 8-column grid; inventories move to a collapsible bottom sheet or side drawers.
- **Mobile**: 4-column grid; the board occupies the top half of the screen, with interactive cards and stats in the bottom half.

Spacing is based on an 8px base unit. Board tiles are separated by a consistent `board-tile-gap` to maintain the "piece-based" feel of a physical game.

## Elevation & Depth

This design system uses **Ambient Shadows** and **Tonal Layers** rather than heavy gradients. 

1.  **Level 0 (Floor)**: The Emerald Green board background.
2.  **Level 1 (Surface)**: Property tiles and secondary buttons. These use a 1px inside stroke of `neutral_slate_200`.
3.  **Level 2 (Lifted)**: Active property cards and player tokens. These feature a soft, diffused shadow (10% opacity Deep Slate, 8px blur) to suggest they are physical objects resting on the board.
4.  **Level 3 (Overlay)**: Main menus, trade modals, and "Chance" cards. These use a high-contrast white background with a more pronounced shadow (15% opacity, 16px blur) to command full focus.

## Shapes

The design system utilizes **Pill-shaped (Level 3)** roundedness to emphasize the "fun" and "friendly" aspect of the game. 

- **Cards & Modals**: Use a 1.5rem (`rounded-xl`) corner radius.
- **Buttons & Player Badges**: Use fully rounded (pill) shapes.
- **Board Tiles**: Use a slightly more conservative 0.5rem radius to ensure they fit together snugly while still feeling soft.

## Components

### Buttons
Primary actions (e.g., "Roll Dice", "Buy Property") are pill-shaped, using the **Cobalt Blue** or **Emerald Green** with white bold typography. Secondary actions use the **Crisp White** surface with a Deep Slate border.

### Property Cards
Modeled after traditional title deeds but updated with digital clarity. The top header uses the property group color. The center contains a clean vertical list of rent prices using `body-stats` with aligned currency symbols.

### Player Tokens
Tokens are represented by vibrant, solid-colored circles containing a white icon or initial. In the "Active" state, they pulse with a subtle glow of their own accent color.

### Game State Chips
Used for quick status updates (e.g., "In Jail", "Mortgaged", "Full Set"). These are small, high-contrast labels with `label-caps` typography and 100px border-radius.

### Input Fields
Used primarily in trading or bidding. These should be large and easy to tap, with 16px padding and a focus state that uses a 2px Cobalt Blue ring.