package view;

import controller.CardController;
import controller.GameController;
import controller.JailController;
import controller.PropertyController;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.*;
import util.Constants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BoardView extends JFrame {

    private static final Color BG = new Color(0xEC, 0xF3, 0xEC);
    private static final Color NAV_BG = new Color(0xF4, 0xFB, 0xF4);
    private static final Color CARD_WHITE = Color.WHITE;
    private static final Color PRIMARY = new Color(0x00, 0x6C, 0x49);
    private static final Color EMERALD = new Color(0x10, 0xB9, 0x81);
    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color MUTED = new Color(0x5C, 0x66, 0x60);
    private static final Color FIELD_BORDER = new Color(0xD8, 0xE2, 0xDB);
    private static final Color TILE_BORDER = new Color(0xE4, 0xEA, 0xE5);
    private static final Color BOARD_FRAME = new Color(0x16, 0x1D, 0x19);
    private static final Color CHANCE_BG = new Color(0xDC, 0xF1, 0xE4);
    private static final Color PLAYER_CARD_BG = new Color(0xED, 0xF4, 0xEE);
    private static final Color WATERMARK = new Color(0x10, 0xB9, 0x81, 36);

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
    private JButton endTurnButton;
    private JButton rollDiceButton;
    private GameEngine gameEngine;
    private GameController gameController;
    private BoardStage boardStage;
    private Map<Player, Color> playerColors;
    private Map<Player, Image> playerTokens;
    private final DiceView diceView = new DiceView();
    private boolean gameOverShown;
    private JLabel turnLabel;
    private JLabel currentPlayerName;
    private JLabel currentPlayerBalance;

    BoardView(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.playerColors = new HashMap<>();
        this.playerTokens = new HashMap<>();

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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "BoardView keeps the shared GameController used by button listeners.")
    public void setController(GameController controller) {
        this.gameController = controller;
        if (rollDiceButton != null) {
            rollDiceButton.addActionListener(e -> gameController.playTurn());
        }
        if (endTurnButton != null) {
            endTurnButton.addActionListener(e -> gameController.handleEndTurn());
        }
    }

    public void refresh() {
        List<Player> activePlayers = gameEngine.getActivePlayers();
        initializePlayerColors(activePlayers);
        updateCurrentPlayer(activePlayers);
        if (boardStage != null) {
            boardStage.refreshPills();
            boardStage.repaintTokens();
        }
        showWinnerIfGameOver();
    }


    private void showWinnerIfGameOver() {
        if (gameOverShown || !gameEngine.isGameOver()) {
            return;
        }
        gameOverShown = true;
        if (turnLabel != null) {
            turnLabel.setText("Game Over");
        }
        if (rollDiceButton != null) {
            rollDiceButton.setEnabled(false);
        }
        if (endTurnButton != null) {
            endTurnButton.setEnabled(false);
        }
        String message = gameEngine.getWinner()
                .map(winner -> winner.getName() + " wins!")
                .orElse("Game over.");
        JOptionPane.showMessageDialog(this, message, "Emerald Estate", JOptionPane.INFORMATION_MESSAGE);
    }


    private void updateCurrentPlayer(List<Player> activePlayers) {
        if (activePlayers.isEmpty()) {
            return;
        }
        Player current = gameEngine.getCurrentPlayer();
        if (turnLabel != null) {
            turnLabel.setText(current.getName() + "'s Turn");
        }
        if (currentPlayerName != null) {
            currentPlayerName.setText(current.getName());
        }
        if (currentPlayerBalance != null) {
            currentPlayerBalance.setText("$" + (int) current.getBalance() + " Balance");
        }
    }

    public void updatePlayerPosition(Player player, int position) {
        if (boardStage != null) {
            boardStage.repaintTokens();
        }
    }

    private void initializePlayerColors(List<Player> players) {
        Color[] colors = {
                new Color(0xE5, 0x3E, 0x3E),
                new Color(0x3B, 0x82, 0xF6),
                new Color(0xF5, 0xB0, 0x36),
                new Color(0x10, 0xB9, 0x81)
        };
        for (int i = 0; i < players.size(); i++) {
            playerColors.put(players.get(i), colors[i % colors.length]);
        }
    }

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
        turnLabel = new JLabel("Player 1's Turn");
        turnLabel.setFont(font(Font.BOLD, 18));
        turnLabel.setForeground(INK);
        left.add(brand);
        left.add(divider);
        left.add(turnLabel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(new JLabel(new BoardIcon(BoardIcon.Type.HISTORY, 22, PRIMARY)));
        right.add(new JLabel(new BoardIcon(BoardIcon.Type.GEAR, 22, PRIMARY)));

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        bar.add(new JSeparator(), BorderLayout.SOUTH);
        return bar;
    }

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
        nav.add(boardNav);
        nav.add(Box.createVerticalStrut(8));
        nav.add(tradeNav);
        nav.add(Box.createVerticalStrut(8));
        nav.add(portfolioNav);

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
        currentPlayerName = new JLabel("Current Player");
        currentPlayerName.setFont(font(Font.BOLD, 14));
        currentPlayerName.setForeground(INK);
        currentPlayerName.setAlignmentX(Component.LEFT_ALIGNMENT);
        currentPlayerBalance = new JLabel("$1,500 Balance");
        currentPlayerBalance.setFont(font(Font.PLAIN, 12));
        currentPlayerBalance.setForeground(EMERALD);
        currentPlayerBalance.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.add(currentPlayerName);
        text.add(Box.createVerticalStrut(2));
        text.add(currentPlayerBalance);

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

    private JPanel createBoardArea() {
        JPanel area = new JPanel(new GridBagLayout());
        area.setBackground(BG);

        boardStage = new BoardStage();
        boardStage.setPreferredSize(new Dimension(740, 660));
        area.add(boardStage, new GridBagConstraints());
        return area;
    }

    private class BoardStage extends JLayeredPane {

        private JPanel pillsContainer;
        private TokenOverlay tokenOverlay;

        BoardStage() {
            setLayout(null);

            JPanel frame = new BoardFrame();
            frame.setBounds(60, 56, 600, 560);
            add(frame, Integer.valueOf(0));

            BoardRing ring = new BoardRing();
            ring.setBounds(74, 70, 572, 532);
            add(ring, Integer.valueOf(1));

            refreshPills();

            JPanel dice = createDiceCluster();
            dice.setBounds(285, 430, 150, 64);
            add(dice, Integer.valueOf(3));

            rollDiceButton = new RoundedButton("Roll Dice", EMERALD, Color.WHITE, 24, null);
            rollDiceButton.setIcon(new BoardIcon(BoardIcon.Type.DIE, 18, Color.WHITE));
            rollDiceButton.setIconTextGap(8);
            rollDiceButton.setFont(font(Font.BOLD, 16));
            rollDiceButton.setBounds(265, 498, 190, 44);
            add(rollDiceButton, Integer.valueOf(3));

            JComponent diceReadout = diceView.getComponent();
            diceReadout.setBounds(265, 410, 190, 20);
            add(diceReadout, Integer.valueOf(3));

            tokenOverlay = new TokenOverlay();
            tokenOverlay.setBounds(0, 0, 740, 660);
            add(tokenOverlay, Integer.valueOf(10));
        }

        void refreshPills() {
            if (pillsContainer != null) {
                remove(pillsContainer);
            }
            pillsContainer = new JPanel();
            pillsContainer.setLayout(new BoxLayout(pillsContainer, BoxLayout.Y_AXIS));
            pillsContainer.setOpaque(false);

            List<Player> players = gameEngine.getActivePlayers();
            Player current = gameEngine.getCurrentPlayer();
            boolean first = true;
            for (Player p : players) {
                if (p.equals(current)) {
                    continue;
                }
                if (!first) {
                    pillsContainer.add(Box.createVerticalStrut(8));
                }
                Color c = playerColors.getOrDefault(p, MUTED);
                String text = p.getName() + ": $" + (int) p.getBalance();
                pillsContainer.add(createPlayerPill(c, text, null));
                first = false;
            }

            int nonCurrentCount = Math.max(0, players.size() - 1);
            int pillsHeight = nonCurrentCount * 36 + Math.max(0, nonCurrentCount - 1) * 8;
            pillsContainer.setBounds(456, 4, 250, Math.max(36, pillsHeight));
            add(pillsContainer, Integer.valueOf(2));
            revalidate();
            repaint();
        }

        void repaintTokens() {
            if (tokenOverlay != null) {
                tokenOverlay.repaint();
            }
        }
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

    private java.awt.Point tileCenter(int tileIndex) {
        int ringX = 74;
        int ringY = 70;
        int ringW = 572;
        int ringH = 532;
        double cellW = (double) ringW / SIDE;
        double cellH = (double) ringH / SIDE;
        int row;
        int col;
        if (tileIndex <= 8) {
            row = 0;
            col = tileIndex;
        } else if (tileIndex <= 15) {
            row = tileIndex - 8;
            col = 8;
        } else if (tileIndex <= 24) {
            row = 8;
            col = 24 - tileIndex;
        } else {
            row = 32 - tileIndex;
            col = 0;
        }
        int x = ringX + (int) (col * cellW + cellW / 2);
        int y = ringY + (int) (row * cellH + cellH / 2);
        return new java.awt.Point(x, y);
    }


    private class TokenOverlay extends JPanel {

        TokenOverlay() {
            setOpaque(false);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (playerColors.isEmpty()) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            List<Player> players = gameEngine.getActivePlayers();
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                Color c = playerColors.get(p);
                if (c == null) {
                    continue;
                }
                int pos;
                try {
                    pos = gameEngine.getPlayerPosition(p);
                } catch (IllegalArgumentException e) {
                    continue;
                }
                java.awt.Point centre = tileCenter(pos);

                int dx = (i % 2) * 14 - 7;
                int dy = (i / 2) * 14 - 7;
                int r = 12;
                int cx = centre.x + dx;
                int cy = centre.y + dy;


                g2.setColor(c);
                g2.fillOval(cx - r, cy - r, r * 2, r * 2);
                g2.setColor(Color.WHITE);
                g2.fillOval(cx - r + 2, cy - r + 2, (r - 2) * 2, (r - 2) * 2);

                Image token = playerTokens.get(p);
                if (token != null) {
                    int d = (r - 2) * 2;
                    g2.drawImage(token, cx - d / 2, cy - d / 2, d, d, this);
                } else {
                    g2.setColor(c);
                    g2.setFont(font(Font.BOLD, 11));
                    String label = String.valueOf(i + 1);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(label,
                            cx - fm.stringWidth(label) / 2,
                            cy + fm.getAscent() / 2 - 1);
                }
            }
            g2.dispose();
        }
    }



    private static final int SIDE = 9;


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


            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = SIDE - 2;
            c.gridheight = SIDE - 2;
            c.weightx = 0;
            c.weighty = 0;
            add(new CenterPanel(), c);
        }
    }


    private TileDef[][] buildGrid() {
        TileDef[][] g = new TileDef[SIDE][SIDE];



        TileDef[] top = new TileDef[SIDE];
        for (int col = 0; col < SIDE; col++) {
            top[col] = tileToTileDef(gameEngine.getTile(col), BarSide.BOTTOM);
        }
        for (int col = 0; col < SIDE; col++) {
            g[0][col] = top[col];
        }


        TileDef[] right = new TileDef[7];
        for (int i = 0; i < 7; i++) {
            right[i] = tileToTileDef(gameEngine.getTile(9 + i), BarSide.LEFT);
        }
        for (int i = 0; i < right.length; i++) {
            g[i + 1][SIDE - 1] = right[i];
        }


        TileDef[] bottom = new TileDef[SIDE];
        for (int col = 0; col < SIDE; col++) {
            bottom[col] = tileToTileDef(gameEngine.getTile(16 + col), BarSide.TOP);
        }
        for (int col = 0; col < SIDE; col++) {
            g[SIDE - 1][SIDE - 1 - col] = bottom[col];
        }


        TileDef[] left = new TileDef[7];
        for (int i = 0; i < 7; i++) {
            left[i] = tileToTileDef(gameEngine.getTile(25 + i), BarSide.RIGHT);
        }
        for (int i = 0; i < left.length; i++) {
            left[i].bar = BarSide.RIGHT;
            g[SIDE - 2 - i][0] = left[i];
        }

        return g;
    }

    private TileDef tileToTileDef(Tile tile, BarSide bar) {
        if (tile instanceof Property) {
            Property prop = (Property) tile;
            String label = "P " + (int) prop.getPrice();
            Color group = getPropertyGroupColor((int) prop.getPrice());
            TileDef def = TileDef.property(label, group);
            def.bar = bar;
            return def;
        } else if (tile instanceof ChanceTile) {
            return TileDef.chanceTile();
        } else if (tile instanceof IRSTile) {
            return TileDef.irs();
        } else if (tile instanceof GoTile) {
            return TileDef.corner(Kind.GO);
        } else if (tile instanceof GoToJailTile) {
            return TileDef.corner(Kind.GOTOJAIL);
        } else if (tile instanceof JailTile) {
            return TileDef.corner(Kind.JAIL);
        } else if (tile instanceof FreeParking) {
            return TileDef.corner(Kind.FREE);
        }
        return TileDef.property("?", NAV_BG);
    }

    private Color getPropertyGroupColor(int price) {

        if (price >= 320) {
            return G_DARK;
        }
        if (price >= 260) {
            return G_BLUE;
        }
        if (price >= 200) {
            return G_RED;
        }
        if (price >= 180) {
            return G_SLATE;
        }
        if (price >= 140) {
            return G_GREEN;
        }
        if (price >= 100) {
            return G_MINT;
        }
        if (price >= 80) {
            return G_RED;
        }
        return G_STEEL;
    }



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



    private enum Kind { PROPERTY, IRS, CHANCE, GO, JAIL, GOTOJAIL, FREE }

    private enum BarSide { TOP, BOTTOM, LEFT, RIGHT, NONE }


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


    private static class TilePanel extends JPanel {
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
                case GO:
                    JLabel go = new JLabel("GO", SwingConstants.CENTER);
                    go.setFont(font(Font.BOLD, 15));
                    go.setForeground(PRIMARY);
                    add(go);
                    break;
                case JAIL:
                    JPanel jail = new JPanel();
                    jail.setOpaque(false);
                    jail.setLayout(new BoxLayout(jail, BoxLayout.Y_AXIS));
                    JLabel lock = new JLabel(new BoardIcon(BoardIcon.Type.LOCK, 18, INK));
                    lock.setAlignmentX(Component.CENTER_ALIGNMENT);
                    JLabel jailLabel = new JLabel("JAIL");
                    jailLabel.setFont(font(Font.BOLD, 10));
                    jailLabel.setForeground(INK);
                    jailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    jail.add(lock);
                    jail.add(Box.createVerticalStrut(2));
                    jail.add(jailLabel);
                    add(jail);
                    break;
                case GOTOJAIL:
                    add(new JLabel(new BoardIcon(BoardIcon.Type.GOTOJAIL, 22,
                            new Color(0xE5, 0x3E, 0x3E))));
                    break;
                case FREE:
                    JLabel free = new JLabel("<html><div style='text-align:center'>FREE<br>PARKING</div></html>",
                            SwingConstants.CENTER);
                    free.setFont(font(Font.BOLD, 9));
                    free.setForeground(MUTED);
                    add(free);
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



    private static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }




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
            double outerRadius = s * 0.42;
            double innerRadius = s * 0.30;
            GeneralPath gear = new GeneralPath();
            int teeth = 8;
            for (int i = 0; i < teeth * 2; i++) {
                double ang = Math.PI * i / teeth;
                double r = (i % 2 == 0) ? outerRadius : innerRadius;
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



    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "BoardView exposes live Swing controls for view wiring and UI tests.")
    public JButton getBoardNav() {
        return boardNav;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "BoardView exposes live Swing controls for view wiring and UI tests.")
    public JButton getTradeNav() {
        return tradeNav;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "BoardView exposes live Swing controls for view wiring and UI tests.")
    public JButton getPortfolioNav() {
        return portfolioNav;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "BoardView exposes live Swing controls for view wiring and UI tests.")
    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "BoardView exposes live Swing controls for view wiring and UI tests.")
    public JButton getRollDiceButton() {
        return rollDiceButton;
    }


    private static Board standardBoard() {
        java.util.List<Tile> tiles = new java.util.ArrayList<>();
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (i == 0) {
                tiles.add(new GoTile());
            } else if (i == Constants.JAIL_POSITION) {
                tiles.add(new JailTile());
            } else if (i == 16) {
                tiles.add(new FreeParking());
            } else if (i == 24) {
                tiles.add(new GoToJailTile());
            } else if (i % 7 == 0) {
                tiles.add(new ChanceTile());
            } else if (i % 9 == 0) {
                tiles.add(new IRSTile());
            } else {
                double price = 60 + (i * 20);
                tiles.add(new Property("Estate " + i, price, price / 10));
            }
        }
        return new Board(tiles);
    }




    private static GameEngine buildEngine(List<Player> players) {
        Board board = standardBoard();
        for (Player p : players) {
            board.setPlayerPosition(p, Constants.GO_POSITION);
        }
        GameEngine engine = new GameEngine(players, board, ChanceDeckFactory.standardDeck());
        if (engine.getStatus() != GameStatus.IN_PROGRESS) {
            engine.startGame();
        }
        return engine;
    }







    public static BoardView launch(List<Player> players) {
        return launch(players, null);
    }







    public static BoardView launch(List<Player> players, List<ImageIcon> tokenIcons) {
        GameEngine engine = buildEngine(players);
        BoardView view = new BoardView(engine);
        view.assignTokens(players, tokenIcons);

        Dice dice = new Dice(new java.util.Random());
        CardView cardView = new CardView(view);
        GameController controller = new GameController(
                engine, view, new PlayerInfoView(), view.diceView, cardView, dice);

        controller.setPropertyController(new PropertyController());
        controller.setCardController(new CardController(engine.getChanceDeck(), engine));
        controller.setJailController(new JailController(engine, dice));
        controller.setPropertyPromptView(new PropertyPromptView(view));
        cardView.setProceedListener(event -> controller.applyDrawnCard());

        view.setController(controller);
        controller.refreshViews();
        view.setVisible(true);
        return view;
    }


    private void assignTokens(List<Player> players, List<ImageIcon> tokenIcons) {
        if (tokenIcons == null) {
            return;
        }
        for (int i = 0; i < players.size() && i < tokenIcons.size(); i++) {
            ImageIcon icon = tokenIcons.get(i);
            if (icon != null) {
                playerTokens.put(players.get(i), icon.getImage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Player> players = new java.util.ArrayList<>();
                players.add(new Player("Player 1", Constants.STARTING_BALANCE));
                players.add(new Player("Player 2", Constants.STARTING_BALANCE));
                launch(players);
            } catch (Exception e) {
                System.err.println("Error: failed to launch BoardView preview");
                e.printStackTrace();
            }
        });
    }
}
