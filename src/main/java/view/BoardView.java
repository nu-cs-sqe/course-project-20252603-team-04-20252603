package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

/**
 * In-game 9x9 board screen for the Emerald Estate edition.
 *
 * <p>The 32 playable tiles are arranged around the perimeter of a 9x9 grid (9 tiles per side, with
 * the four corners shared). The hollow centre hosts the active Chance / property card overlay and a
 * faint "EMERALD ESTATE" watermark.</p>
 *
 * <p>As with {@link MainMenuView}, every icon is rendered with Graphics2D (no emoji / unicode
 * glyphs) so the screen looks identical regardless of the host fonts or the source-file encoding.</p>
 */
public class BoardView extends JFrame {

    // ----- Palette (Stitch design tokens) ----------------------------------------------------
    private static final Color BG = new Color(0xEC, 0xF3, 0xEC);          // board backdrop
    private static final Color NAV_BG = new Color(0xF4, 0xFB, 0xF4);      // top bar / sidebar
    private static final Color CARD_WHITE = Color.WHITE;
    private static final Color PRIMARY = new Color(0x00, 0x6C, 0x49);     // dark green text
    private static final Color EMERALD = new Color(0x10, 0xB9, 0x81);     // accent / active pill
    private static final Color INK = new Color(0x16, 0x1D, 0x19);         // primary text
    private static final Color MUTED = new Color(0x5C, 0x66, 0x60);       // secondary text
    private static final Color FIELD_BORDER = new Color(0xD8, 0xE2, 0xDB);
    private static final Color TILE_BORDER = new Color(0xE4, 0xEA, 0xE5);
    private static final Color BOARD_FRAME = new Color(0x16, 0x1D, 0x19); // dark ring around board
    private static final Color CHANCE_BG = new Color(0xDC, 0xF1, 0xE4);   // light green chance tile
    private static final Color PLAYER_CARD_BG = new Color(0xED, 0xF4, 0xEE);
    private static final Color WATERMARK = new Color(0x10, 0xB9, 0x81, 36);

    // Property group accents.
    private static final Color G_RED = new Color(0xF1, 0xCB, 0xCB);
    private static final Color G_DARK = new Color(0x3F, 0x4A, 0x45);
    private static final Color G_BLUE = new Color(0xC8, 0xD3, 0xEC);
    private static final Color G_SLATE = new Color(0x9A, 0xA6, 0xC4);
    private static final Color G_GREEN = new Color(0x10, 0xB9, 0x81);
    private static final Color G_MINT = new Color(0xCF, 0xCF, 0xE6);
    private static final Color G_STEEL = new Color(0xC2, 0xCB, 0xD6);

    private static final String FONT_FAMILY = "Segoe UI";

    private JButton boardNav;
    private JButton tradeNav;
    private JButton portfolioNav;
    private JButton auctionNav;
    private JButton endTurnButton;
    private JButton rollDiceButton;

    public BoardView() {
        setTitle("Emerald Estate - Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 860);
        setMinimumSize(new Dimension(1100, 760));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(createTopBar(), BorderLayout.NORTH);
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createBoardArea(), BorderLayout.CENTER);
        setContentPane(root);
    }

    // ============================================================ Top bar =====================

    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(NAV_BG);
        bar.setBorder(new EmptyBorder(0, 24, 0, 24));
        bar.setPreferredSize(new Dimension(0, 56));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        JLabel brand = new JLabel("Empire Tycoon");
        brand.setFont(font(Font.BOLD, 20));
        brand.setForeground(PRIMARY);
        JLabel divider = new JLabel("   |   ");
        divider.setFont(font(Font.PLAIN, 20));
        divider.setForeground(FIELD_BORDER);
        JLabel turn = new JLabel("Player 1's Turn");
        turn.setFont(font(Font.BOLD, 18));
        turn.setForeground(INK);
        left.add(brand);
        left.add(divider);
        left.add(turn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(new JLabel(new BoardIcon(BoardIcon.Type.HISTORY, 22, PRIMARY)));
        right.add(new JLabel(new BoardIcon(BoardIcon.Type.GEAR, 22, PRIMARY)));

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        bar.add(new JSeparator(), BorderLayout.SOUTH);
        return bar;
    }

    // ============================================================ Sidebar =====================

    private JPanel createSidebar() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(NAV_BG);
        nav.setBorder(new EmptyBorder(20, 16, 20, 16));
        nav.setPreferredSize(new Dimension(250, 0));

        nav.add(createCurrentPlayerCard());
        nav.add(Box.createVerticalStrut(24));

        boardNav = createNavItem(BoardIcon.Type.NAV_BOARD, "Board", true);
        tradeNav = createNavItem(BoardIcon.Type.NAV_TRADE, "Trade", false);
        portfolioNav = createNavItem(BoardIcon.Type.NAV_PORTFOLIO, "Portfolio", false);
        auctionNav = createNavItem(BoardIcon.Type.NAV_AUCTION, "Auction", false);
        nav.add(boardNav);
        nav.add(Box.createVerticalStrut(8));
        nav.add(tradeNav);
        nav.add(Box.createVerticalStrut(8));
        nav.add(portfolioNav);
        nav.add(Box.createVerticalStrut(8));
        nav.add(auctionNav);

        nav.add(Box.createVerticalGlue());

        JLabel owned = new JLabel("OWNED PROPERTIES");
        owned.setFont(font(Font.BOLD, 11));
        owned.setForeground(MUTED);
        owned.setAlignmentX(Component.LEFT_ALIGNMENT);
        nav.add(owned);
        nav.add(Box.createVerticalStrut(10));

        JPanel swatches = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        swatches.setOpaque(false);
        swatches.setAlignmentX(Component.LEFT_ALIGNMENT);
        swatches.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        swatches.add(swatch(G_GREEN));
        swatches.add(swatch(G_SLATE));
        nav.add(swatches);
        nav.add(Box.createVerticalStrut(16));

        endTurnButton = new RoundedButton("End Turn", INK, Color.WHITE, 14, null);
        endTurnButton.setFont(font(Font.BOLD, 15));
        endTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        endTurnButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        endTurnButton.setPreferredSize(new Dimension(220, 52));
        nav.add(endTurnButton);

        return nav;
    }

    private JComponent swatch(Color color) {
        JPanel p = new RoundedPanel(color, 6);
        p.setPreferredSize(new Dimension(20, 20));
        p.setMaximumSize(new Dimension(20, 20));
        return p;
    }

    private JPanel createCurrentPlayerCard() {
        RoundedPanel card = new RoundedPanel(PLAYER_CARD_BG, 16);
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 12));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        RoundedPanel avatar = new RoundedPanel(CARD_WHITE, 14);
        avatar.setLayout(new GridBagLayout());
        avatar.setPreferredSize(new Dimension(44, 44));
        avatar.add(new JLabel(new BoardIcon(BoardIcon.Type.TOKENS, 30, EMERALD)));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);
        JLabel name = new JLabel("Current Player");
        name.setFont(font(Font.BOLD, 14));
        name.setForeground(INK);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel balance = new JLabel("$1,500 Balance");
        balance.setFont(font(Font.PLAIN, 12));
        balance.setForeground(EMERALD);
        balance.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.add(name);
        text.add(Box.createVerticalStrut(2));
        text.add(balance);

        card.add(avatar);
        card.add(text);
        return card;
    }

    private JButton createNavItem(BoardIcon.Type icon, String label, boolean active) {
        Color bg = active ? EMERALD : NAV_BG;
        Color fg = active ? Color.WHITE : MUTED;
        RoundedButton btn = new RoundedButton(label, bg, fg, 12, null);
        btn.setIcon(new BoardIcon(icon, 20, fg));
        btn.setFont(font(active ? Font.BOLD : Font.PLAIN, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(14);
        btn.setBorder(new EmptyBorder(12, 16, 12, 16));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        return btn;
    }

    // ============================================================ Board area ==================

    private JPanel createBoardArea() {
        JPanel area = new JPanel(new GridBagLayout());
        area.setBackground(BG);

        BoardStage stage = new BoardStage();
        stage.setPreferredSize(new Dimension(740, 660));
        area.add(stage, new GridBagConstraints());
        return area;
    }

    /** Fixed-size layered stage: dark board frame, the tile ring, centre card and floating chrome. */
    private class BoardStage extends JLayeredPane {

        BoardStage() {
            setLayout(null);

            JPanel frame = new BoardFrame();
            frame.setBounds(60, 56, 600, 560);
            add(frame, Integer.valueOf(0));

            BoardRing ring = new BoardRing();
            ring.setBounds(74, 70, 572, 532);
            add(ring, Integer.valueOf(1));

            JPanel pills = createPlayerPills();
            pills.setBounds(456, 4, 250, 90);
            add(pills, Integer.valueOf(2));

            JLabel freeParking = new JLabel("FREE PARKING");
            freeParking.setFont(font(Font.BOLD, 9));
            freeParking.setForeground(MUTED);
            freeParking.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel freeChip = new RoundedPanel(NAV_BG, 12);
            freeChip.setLayout(new GridBagLayout());
            freeChip.add(freeParking);
            freeChip.setBounds(540, 480, 96, 26);
            add(freeChip, Integer.valueOf(3));

            JPanel dice = createDiceCluster();
            dice.setBounds(548, 502, 150, 64);
            add(dice, Integer.valueOf(3));

            rollDiceButton = new RoundedButton("Roll Dice", EMERALD, Color.WHITE, 24, null);
            rollDiceButton.setIcon(new BoardIcon(BoardIcon.Type.DIE, 18, Color.WHITE));
            rollDiceButton.setIconTextGap(8);
            rollDiceButton.setFont(font(Font.BOLD, 16));
            rollDiceButton.setBounds(556, 574, 150, 48);
            add(rollDiceButton, Integer.valueOf(3));
        }
    }

    private JPanel createPlayerPills() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);
        col.add(createPlayerPill(new Color(0xE5, 0x3E, 0x3E), "P2: $1,240", "IN JAIL"));
        col.add(Box.createVerticalStrut(8));
        col.add(createPlayerPill(new Color(0x3B, 0x82, 0xF6), "P3: $890", null));
        return col;
    }

    private JComponent createPlayerPill(Color dot, String text, String tag) {
        RoundedPanel pill = new RoundedPanel(CARD_WHITE, 18);
        pill.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 7));
        pill.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pill.setMaximumSize(new Dimension(200, 36));

        JPanel d = new RoundedPanel(dot, 10);
        d.setPreferredSize(new Dimension(12, 12));
        JLabel label = new JLabel(text);
        label.setFont(font(Font.BOLD, 13));
        label.setForeground(INK);
        pill.add(d);
        pill.add(label);
        if (tag != null) {
            JLabel tagLabel = new JLabel(tag);
            tagLabel.setFont(font(Font.BOLD, 10));
            tagLabel.setForeground(new Color(0xE5, 0x3E, 0x3E));
            pill.add(tagLabel);
        }
        return pill;
    }

    private JPanel createDiceCluster() {
        JPanel cluster = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        cluster.setOpaque(false);
        cluster.add(new JLabel(new BoardIcon(BoardIcon.Type.DIE_FACE, 52, INK)));
        cluster.add(new JLabel(new BoardIcon(BoardIcon.Type.DIE_DARK, 52, INK)));
        return cluster;
    }

    // ------------------------------------------------------------ Tile ring -------------------

    private static final int SIDE = 9; // 9 tiles per edge

    /** The 32-tile ring laid out on a 9x9 GridBag; the hollow centre carries the card overlay. */
    private class BoardRing extends JPanel {

        BoardRing() {
            setLayout(new GridBagLayout());
            setOpaque(false);

            TileDef[][] grid = buildGrid();
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            c.weighty = 1;
            c.insets = new Insets(2, 2, 2, 2);

            for (int row = 0; row < SIDE; row++) {
                for (int col = 0; col < SIDE; col++) {
                    TileDef def = grid[row][col];
                    if (def == null) {
                        continue;
                    }
                    c.gridx = col;
                    c.gridy = row;
                    add(new TilePanel(def), c);
                }
            }

            // Hollow centre (7x7) holds the active card + watermark.
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = SIDE - 2;
            c.gridheight = SIDE - 2;
            c.weightx = 0;
            c.weighty = 0;
            add(new CenterPanel(), c);
        }
    }

    /** Builds the 9x9 grid with the 32 perimeter tiles populated (interior left null). */
    private TileDef[][] buildGrid() {
        TileDef[][] g = new TileDef[SIDE][SIDE];

        // Top edge (row 0), left to right; accent bar faces inward (bottom).
        TileDef[] top = {
                TileDef.corner(Kind.CHANCE), prop(120, G_RED), prop(300, G_DARK),
                TileDef.chanceTile(), prop(250, G_BLUE), prop(120, G_RED),
                prop(250, G_BLUE), prop(140, G_BLUE), TileDef.corner(Kind.GOTOJAIL)
        };
        for (int col = 0; col < SIDE; col++) {
            top[col].bar = BarSide.BOTTOM;
            g[0][col] = top[col];
        }

        // Right edge (col 8), rows 1..7; bar faces inward (left).
        TileDef[] right = {
                prop(200, G_GREEN), TileDef.chanceTile(), prop(180, G_GREEN),
                prop(180, G_GREEN), TileDef.irs(), prop(160, G_GREEN), prop(160, G_GREEN)
        };
        for (int i = 0; i < right.length; i++) {
            right[i].bar = BarSide.LEFT;
            g[i + 1][SIDE - 1] = right[i];
        }

        // Bottom edge (row 8), left to right; bar faces inward (top).
        TileDef[] bottom = {
                TileDef.corner(Kind.JAIL), prop(100, G_STEEL), prop(80, G_MINT),
                prop(80, G_MINT), TileDef.chanceTile(), prop(60, G_GREEN),
                TileDef.irs(), prop(60, G_GREEN), TileDef.corner(Kind.FREE)
        };
        for (int col = 0; col < SIDE; col++) {
            bottom[col].bar = BarSide.TOP;
            g[SIDE - 1][col] = bottom[col];
        }

        // Left edge (col 0), rows 1..7; bar faces inward (right).
        TileDef[] left = {
                prop(120, G_RED), prop(350, G_SLATE), TileDef.irs(), prop(400, G_GREEN),
                TileDef.irs(), prop(120, G_RED), TileDef.irs()
        };
        for (int i = 0; i < left.length; i++) {
            left[i].bar = BarSide.RIGHT;
            g[i + 1][0] = left[i];
        }

        return g;
    }

    private static TileDef prop(int price, Color group) {
        return TileDef.property("P " + price, group);
    }

    // ------------------------------------------------------------ Centre overlay --------------

    private class CenterPanel extends JPanel {
        CenterPanel() {
            setLayout(new GridBagLayout());
            setOpaque(false);
            add(createChanceCard());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(font(Font.BOLD, 44));
            g2.setColor(WATERMARK);
            g2.rotate(Math.toRadians(-20), getWidth() / 2.0, getHeight() / 2.0);
            String mark = "EMERALD ESTATE";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(mark, (getWidth() - fm.stringWidth(mark)) / 2,
                    getHeight() / 2 + fm.getAscent() / 2);
            g2.dispose();
        }
    }

    private JComponent createChanceCard() {
        RoundedPanel card = new RoundedPanel(new Color(0xFF, 0xFF, 0xFF, 235), 18);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(26, 40, 26, 40));
        card.setMaximumSize(new Dimension(360, 150));

        JLabel icon = new JLabel(new BoardIcon(BoardIcon.Type.DIE, 26, INK));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel("CHANCE");
        title.setFont(font(Font.BOLD, 22));
        title.setForeground(INK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel desc = new JLabel("<html><div style='text-align:center;width:300px'>"
                + "<i>\"Advance to the nearest Utility. If unowned, you may buy it...\"</i></div></html>",
                SwingConstants.CENTER);
        desc.setFont(font(Font.PLAIN, 13));
        desc.setForeground(MUTED);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(desc);
        return card;
    }

    // ============================================================ Tile model ==================

    private enum Kind { PROPERTY, IRS, CHANCE, JAIL, GOTOJAIL, FREE }

    private enum BarSide { TOP, BOTTOM, LEFT, RIGHT, NONE }

    /** Lightweight render descriptor for a single board tile. */
    private static class TileDef {
        final Kind kind;
        final String label;
        final Color group;
        BarSide bar = BarSide.NONE;

        private TileDef(Kind kind, String label, Color group) {
            this.kind = kind;
            this.label = label;
            this.group = group;
        }

        static TileDef property(String label, Color group) {
            return new TileDef(Kind.PROPERTY, label, group);
        }

        static TileDef irs() {
            return new TileDef(Kind.IRS, "IRS", null);
        }

        static TileDef chanceTile() {
            return new TileDef(Kind.CHANCE, "?", null);
        }

        static TileDef corner(Kind kind) {
            return new TileDef(kind, null, null);
        }
    }

    /** Renders one tile: white card (or green Chance), an optional group accent bar and a label. */
    private class TilePanel extends JPanel {
        private final TileDef def;

        TilePanel(TileDef def) {
            this.def = def;
            setOpaque(false);
            setPreferredSize(new Dimension(56, 52));
            setLayout(new GridBagLayout());
            addContent();
        }

        private void addContent() {
            switch (def.kind) {
                case PROPERTY:
                    JLabel p = new JLabel(def.label);
                    p.setFont(font(Font.BOLD, 11));
                    p.setForeground(INK);
                    add(p);
                    break;
                case IRS:
                    JPanel irs = new JPanel();
                    irs.setOpaque(false);
                    irs.setLayout(new BoxLayout(irs, BoxLayout.Y_AXIS));
                    JLabel ic = new JLabel(new BoardIcon(BoardIcon.Type.BANK, 16, MUTED));
                    ic.setAlignmentX(Component.CENTER_ALIGNMENT);
                    JLabel it = new JLabel("IRS");
                    it.setFont(font(Font.BOLD, 10));
                    it.setForeground(MUTED);
                    it.setAlignmentX(Component.CENTER_ALIGNMENT);
                    irs.add(ic);
                    irs.add(Box.createVerticalStrut(2));
                    irs.add(it);
                    add(irs);
                    break;
                case CHANCE:
                    JLabel q = new JLabel("?");
                    q.setFont(font(Font.BOLD, 18));
                    q.setForeground(PRIMARY);
                    add(q);
                    break;
                case JAIL:
                    add(new JLabel(new BoardIcon(BoardIcon.Type.LOCK, 22, INK)));
                    break;
                case GOTOJAIL:
                    add(new JLabel(new BoardIcon(BoardIcon.Type.GOTOJAIL, 22,
                            new Color(0xE5, 0x3E, 0x3E))));
                    break;
                case FREE:
                    // The "Free Parking" chip is floated by the stage; corner stays empty.
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            g2.setColor(def.kind == Kind.CHANCE ? CHANCE_BG : CARD_WHITE);
            g2.fillRoundRect(0, 0, w - 1, h - 1, 8, 8);
            g2.setColor(TILE_BORDER);
            g2.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);

            if (def.kind == Kind.PROPERTY && def.group != null) {
                g2.setColor(def.group);
                int t = 8;
                switch (def.bar) {
                    case BOTTOM:
                        g2.fillRect(3, h - t - 2, w - 7, t);
                        break;
                    case TOP:
                        g2.fillRect(3, 2, w - 7, t);
                        break;
                    case LEFT:
                        g2.fillRect(2, 3, t, h - 7);
                        break;
                    case RIGHT:
                        g2.fillRect(w - t - 2, 3, t, h - 7);
                        break;
                    default:
                        break;
                }
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Dark rounded frame drawn behind the tile ring. */
    private static class BoardFrame extends JPanel {
        BoardFrame() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(BOARD_FRAME);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
            g2.setColor(NAV_BG);
            g2.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ============================================================ Helpers =====================

    private static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    // ============================================================ Reusable components ==========

    /** Panel with an anti-aliased rounded-rectangle background. */
    private static class RoundedPanel extends JPanel {
        private final Color color;
        private final int arc;

        RoundedPanel(Color color, int arc) {
            this.color = color;
            this.arc = arc;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Flat, rounded (optionally pill-shaped) button with optional border. */
    private static class RoundedButton extends JButton {
        private final Color bg;
        private final Color border;
        private final int arc;

        RoundedButton(String text, Color bg, Color fg, int arc, Color border) {
            super(text);
            this.bg = bg;
            this.border = border;
            this.arc = arc;
            setForeground(fg);
            setFont(font(Font.BOLD, 13));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2.setColor(getModel().isPressed() ? bg.darker() : bg);
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            if (border != null) {
                g2.setColor(border);
                g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ============================================================ Vector iconography ==========

    /** Small monochrome line/solid icons drawn with Graphics2D. */
    private static class BoardIcon implements Icon {
        enum Type {
            HISTORY, GEAR, NAV_BOARD, NAV_TRADE, NAV_PORTFOLIO, NAV_AUCTION,
            LOCK, GOTOJAIL, BANK, DIE, DIE_FACE, DIE_DARK, TOKENS
        }

        private final Type type;
        private final int size;
        private final Color color;

        BoardIcon(Type type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.translate(x, y);
            g2.setColor(color);
            double s = size;
            float stroke = (float) (s * 0.09);
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            switch (type) {
                case HISTORY:
                    paintHistory(g2, s);
                    break;
                case GEAR:
                    paintGear(g2, s);
                    break;
                case NAV_BOARD:
                    paintBoardGrid(g2, s);
                    break;
                case NAV_TRADE:
                    paintTrade(g2, s);
                    break;
                case NAV_PORTFOLIO:
                    paintHouse(g2, s);
                    break;
                case NAV_AUCTION:
                    paintGavel(g2, s);
                    break;
                case LOCK:
                    paintLock(g2, s);
                    break;
                case GOTOJAIL:
                    paintArrow(g2, s);
                    break;
                case BANK:
                    paintBank(g2, s);
                    break;
                case DIE:
                    paintDieGlyph(g2, s);
                    break;
                case DIE_FACE:
                    paintDie(g2, s, false);
                    break;
                case DIE_DARK:
                    paintDie(g2, s, true);
                    break;
                case TOKENS:
                    paintTokens(g2, s);
                    break;
                default:
                    break;
            }
            g2.dispose();
        }

        private void paintHistory(Graphics2D g2, double s) {
            g2.draw(new Ellipse2D.Double(s * 0.12, s * 0.12, s * 0.76, s * 0.76));
            g2.drawLine((int) (s * 0.5), (int) (s * 0.5), (int) (s * 0.5), (int) (s * 0.28));
            g2.drawLine((int) (s * 0.5), (int) (s * 0.5), (int) (s * 0.68), (int) (s * 0.58));
        }

        private void paintGear(Graphics2D g2, double s) {
            double cx = s * 0.5;
            double cy = s * 0.5;
            double rOuter = s * 0.42;
            double rInner = s * 0.30;
            GeneralPath gear = new GeneralPath();
            int teeth = 8;
            for (int i = 0; i < teeth * 2; i++) {
                double ang = Math.PI * i / teeth;
                double r = (i % 2 == 0) ? rOuter : rInner;
                double px = cx + Math.cos(ang) * r;
                double py = cy + Math.sin(ang) * r;
                if (i == 0) {
                    gear.moveTo(px, py);
                } else {
                    gear.lineTo(px, py);
                }
            }
            gear.closePath();
            g2.fill(gear);
            g2.setColor(NAV_BG);
            g2.fill(new Ellipse2D.Double(cx - s * 0.14, cy - s * 0.14, s * 0.28, s * 0.28));
        }

        private void paintBoardGrid(Graphics2D g2, double s) {
            double u = s * 0.16;
            double gap = s * 0.12;
            double[] xs = {s * 0.18, s * 0.18 + u + gap};
            double[] ys = {s * 0.18, s * 0.18 + u + gap};
            for (double px : xs) {
                for (double py : ys) {
                    g2.fill(new RoundRectangle2D.Double(px, py, u, u, u * 0.3, u * 0.3));
                }
            }
        }

        private void paintTrade(Graphics2D g2, double s) {
            g2.drawLine((int) (s * 0.2), (int) (s * 0.36), (int) (s * 0.8), (int) (s * 0.36));
            g2.drawLine((int) (s * 0.66), (int) (s * 0.22), (int) (s * 0.8), (int) (s * 0.36));
            g2.drawLine((int) (s * 0.8), (int) (s * 0.64), (int) (s * 0.2), (int) (s * 0.64));
            g2.drawLine((int) (s * 0.34), (int) (s * 0.78), (int) (s * 0.2), (int) (s * 0.64));
        }

        private void paintHouse(Graphics2D g2, double s) {
            GeneralPath roof = new GeneralPath();
            roof.moveTo(s * 0.5, s * 0.2);
            roof.lineTo(s * 0.84, s * 0.48);
            roof.lineTo(s * 0.16, s * 0.48);
            roof.closePath();
            g2.fill(roof);
            g2.fill(new RoundRectangle2D.Double(s * 0.26, s * 0.48, s * 0.48, s * 0.32,
                    s * 0.05, s * 0.05));
        }

        private void paintGavel(Graphics2D g2, double s) {
            g2.rotate(Math.toRadians(40), s * 0.5, s * 0.5);
            g2.fill(new RoundRectangle2D.Double(s * 0.24, s * 0.18, s * 0.52, s * 0.22,
                    s * 0.06, s * 0.06));
            g2.fill(new RoundRectangle2D.Double(s * 0.45, s * 0.40, s * 0.10, s * 0.42,
                    s * 0.04, s * 0.04));
        }

        private void paintLock(Graphics2D g2, double s) {
            g2.draw(new java.awt.geom.Arc2D.Double(s * 0.28, s * 0.16, s * 0.44, s * 0.44,
                    0, 180, java.awt.geom.Arc2D.OPEN));
            g2.fill(new RoundRectangle2D.Double(s * 0.22, s * 0.42, s * 0.56, s * 0.42,
                    s * 0.08, s * 0.08));
        }

        private void paintArrow(Graphics2D g2, double s) {
            g2.drawLine((int) (s * 0.22), (int) (s * 0.78), (int) (s * 0.78), (int) (s * 0.22));
            GeneralPath head = new GeneralPath();
            head.moveTo(s * 0.78, s * 0.22);
            head.lineTo(s * 0.5, s * 0.22);
            head.moveTo(s * 0.78, s * 0.22);
            head.lineTo(s * 0.78, s * 0.5);
            g2.draw(head);
        }

        private void paintBank(Graphics2D g2, double s) {
            GeneralPath roof = new GeneralPath();
            roof.moveTo(s * 0.5, s * 0.18);
            roof.lineTo(s * 0.86, s * 0.38);
            roof.lineTo(s * 0.14, s * 0.38);
            roof.closePath();
            g2.fill(roof);
            g2.setStroke(new BasicStroke((float) (s * 0.1)));
            g2.drawLine((int) (s * 0.28), (int) (s * 0.42), (int) (s * 0.28), (int) (s * 0.72));
            g2.drawLine((int) (s * 0.5), (int) (s * 0.42), (int) (s * 0.5), (int) (s * 0.72));
            g2.drawLine((int) (s * 0.72), (int) (s * 0.42), (int) (s * 0.72), (int) (s * 0.72));
            g2.fill(new RoundRectangle2D.Double(s * 0.12, s * 0.74, s * 0.76, s * 0.12,
                    s * 0.03, s * 0.03));
        }

        private void paintDieGlyph(Graphics2D g2, double s) {
            g2.fill(new RoundRectangle2D.Double(s * 0.18, s * 0.18, s * 0.64, s * 0.64,
                    s * 0.16, s * 0.16));
            g2.setColor(NAV_BG);
            double r = s * 0.07;
            double[][] pips = {{0.36, 0.36}, {0.64, 0.36}, {0.5, 0.5}, {0.36, 0.64}, {0.64, 0.64}};
            for (double[] pip : pips) {
                g2.fill(new Ellipse2D.Double(s * pip[0] - r, s * pip[1] - r, r * 2, r * 2));
            }
        }

        private void paintDie(Graphics2D g2, double s, boolean dark) {
            Color body = dark ? INK : Color.WHITE;
            g2.setColor(body);
            g2.fill(new Ellipse2D.Double(s * 0.06, s * 0.06, s * 0.88, s * 0.88));
            g2.setColor(dark ? INK : FIELD_BORDER);
            g2.setStroke(new BasicStroke((float) (s * 0.03)));
            g2.draw(new Ellipse2D.Double(s * 0.06, s * 0.06, s * 0.88, s * 0.88));
            if (!dark) {
                g2.setColor(INK);
                double r = s * 0.07;
                double[][] pips = {{0.34, 0.34}, {0.66, 0.34}, {0.5, 0.5},
                        {0.34, 0.66}, {0.66, 0.66}};
                for (double[] pip : pips) {
                    g2.fill(new Ellipse2D.Double(s * pip[0] - r, s * pip[1] - r, r * 2, r * 2));
                }
            }
        }

        private void paintTokens(Graphics2D g2, double s) {
            g2.setColor(new Color(0xE5, 0x3E, 0x3E));
            g2.fill(new Ellipse2D.Double(s * 0.08, s * 0.30, s * 0.30, s * 0.30));
            g2.setColor(new Color(0x3B, 0x82, 0xF6));
            GeneralPath tri = new GeneralPath();
            tri.moveTo(s * 0.55, s * 0.20);
            tri.lineTo(s * 0.80, s * 0.62);
            tri.lineTo(s * 0.30, s * 0.62);
            tri.closePath();
            g2.fill(tri);
            g2.setColor(new Color(0xF5, 0xB0, 0x36));
            g2.fill(new RoundRectangle2D.Double(s * 0.46, s * 0.52, s * 0.36, s * 0.30,
                    s * 0.05, s * 0.05));
        }
    }

    // ============================================================ Accessors ===================

    public JButton getBoardNav() {
        return boardNav;
    }

    public JButton getTradeNav() {
        return tradeNav;
    }

    public JButton getPortfolioNav() {
        return portfolioNav;
    }

    public JButton getAuctionNav() {
        return auctionNav;
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public JButton getRollDiceButton() {
        return rollDiceButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BoardView().setVisible(true));
    }
}
