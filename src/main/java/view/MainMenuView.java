package view;

import controller.MainMenuController;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.GameEngine;
import model.Player;
import util.LocalizationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainMenuView extends JFrame {

    private static final Color BG = new Color(0xEE, 0xF6, 0xEE);
    private static final Color NAV_BG = new Color(0xF4, 0xFB, 0xF4);
    private static final Color CARD_WHITE = Color.WHITE;
    private static final Color PLAYER_CARD_BG = new Color(0xF1, 0xF7, 0xF2);
    private static final Color PRIMARY = new Color(0x00, 0x6C, 0x49);
    private static final Color EMERALD = new Color(0x10, 0xB9, 0x81);
    private static final Color TRADE_GREEN = new Color(0x0C, 0x7A, 0x4E);
    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color MUTED = new Color(0x5C, 0x66, 0x60);
    private static final Color FIELD_BORDER = new Color(0xD8, 0xE2, 0xDB);
    private static final Color PILL_INACTIVE_BG = new Color(0xE3, 0xEA, 0xE3);
    private static final Color TRADE_SUBTEXT = new Color(0xCF, 0xE9, 0xDC);

    private static final String FONT_FAMILY = "Segoe UI";

    private static final VectorIcon.Type[] TOKEN_SET = {
            VectorIcon.Type.CAR, VectorIcon.Type.HAT, VectorIcon.Type.BOAT, VectorIcon.Type.IRON
    };

    private final List<JTextField> nameFields = new ArrayList<>();
    private final List<VectorIcon.Type> selectedTokens = new ArrayList<>();
    private int selectedCount = 2;
    private JPanel playersPanel;
    private JPanel pillGroup;
    private JPanel assembleCard;
    private JButton newGameButton;
    private JComboBox<String> languageSelector;

    public MainMenuView() {
        setTitle(LocalizationManager.getMessage("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900);
        setMinimumSize(new Dimension(1024, 700));
        setLocationRelativeTo(null);
        setContentPane(createRoot());
        wireActions();
    }

    private JPanel createRoot() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(createNavBar(), BorderLayout.NORTH);

        JPanel body = createBody();
        JScrollPane scroll = new JScrollPane(body,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        return root;
    }

    private void wireActions() {
        if (newGameButton != null) {
            newGameButton.addActionListener(e -> handleNewGame());
        }
    }

    private void handleNewGame() {
        List<String> names = new ArrayList<>();
        List<ImageIcon> icons = new ArrayList<>();
        for (int i = 0; i < nameFields.size(); i++) {
            String typed = nameFields.get(i).getText().trim();
            names.add(typed.isEmpty()
                    ? LocalizationManager.formatMessage("mainMenu.defaultPlayerName", i + 1)
                    : typed);
            icons.add(iconFor(selectedTokens.get(i)));
        }

        try {
            MainMenuController controller = new MainMenuController(names, icons);
            GameEngine startedGame = controller.startNewGame();
            List<Player> players = new ArrayList<>(startedGame.getActivePlayers());
            BoardView.launch(players, icons);
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    LocalizationManager.getMessage("mainMenu.cannotStartTitle"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private ImageIcon iconFor(VectorIcon.Type type) {
        int size = 40;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        new VectorIcon(type, size).paintIcon(this, g, 0, 0);
        g.dispose();
        return new ImageIcon(img);
    }

    private JPanel createNavBar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(NAV_BG);
        nav.setBorder(new EmptyBorder(0, 24, 0, 24));
        nav.setPreferredSize(new Dimension(0, 64));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        left.setOpaque(false);
        left.add(new JLabel(new VectorIcon(VectorIcon.Type.LOGO, 32)));
        JLabel brand = new JLabel(LocalizationManager.getMessage("app.title"));
        brand.setFont(font(Font.BOLD, 22));
        brand.setForeground(PRIMARY);
        left.add(brand);

        nav.add(left, BorderLayout.WEST);
        nav.add(createLanguagePanel(), BorderLayout.EAST);
        nav.add(new JSeparator(), BorderLayout.SOUTH);
        return nav;
    }

    private JPanel createLanguagePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 14));
        panel.setOpaque(false);

        JLabel label = new JLabel(LocalizationManager.getMessage("mainMenu.languageLabel"));
        label.setFont(font(Font.BOLD, 12));
        label.setForeground(MUTED);

        languageSelector = createLanguageSelector();
        panel.add(label);
        panel.add(languageSelector);
        return panel;
    }

    private JComboBox<String> createLanguageSelector() {
        JComboBox<String> selector = new JComboBox<>(new String[]{
                LocalizationManager.getMessage("mainMenu.language.english"),
                LocalizationManager.getMessage("mainMenu.language.spanish")
        });
        selector.setSelectedIndex(isSpanishLocale() ? 1 : 0);
        selector.setFont(font(Font.PLAIN, 13));
        selector.setFocusable(false);
        selector.setPreferredSize(new Dimension(130, 32));
        selector.addActionListener(event -> switchLanguage(selectedLocale(selector)));
        return selector;
    }

    private Locale selectedLocale(JComboBox<String> selector) {
        if (selector.getSelectedIndex() == 1) {
            return LocalizationManager.SPANISH;
        }
        return LocalizationManager.ENGLISH;
    }

    private boolean isSpanishLocale() {
        return LocalizationManager.getLocale().getLanguage()
                .equals(LocalizationManager.SPANISH.getLanguage());
    }

    private void switchLanguage(Locale locale) {
        if (LocalizationManager.getLocale().getLanguage().equals(locale.getLanguage())) {
            return;
        }
        LocalizationManager.setLocale(locale);
        SwingUtilities.invokeLater(this::refreshLocalizedContent);
    }

    private void refreshLocalizedContent() {
        setTitle(LocalizationManager.getMessage("app.title"));
        setContentPane(createRoot());
        wireActions();
        revalidate();
        repaint();
    }

    private JPanel createBody() {
        JPanel body = new ScrollableWidthPanel();
        body.setLayout(new GridBagLayout());
        body.setBackground(BG);
        body.setBorder(new EmptyBorder(32, 80, 48, 80));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;

        c.gridy = 0;
        c.insets = new Insets(0, 0, 36, 0);
        body.add(createHero(), c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 48, 0);
        body.add(createMainRow(), c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        body.add(createShowcaseRow(), c);

        c.gridy = 3;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        body.add(Box.createGlue(), c);

        return body;
    }

    private JPanel createHero() {
        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setOpaque(false);
        hero.setAlignmentX(Component.CENTER_ALIGNMENT);
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        RoundedPanel iconCard = new RoundedPanel(CARD_WHITE, 28);
        iconCard.setLayout(new GridBagLayout());
        iconCard.setPreferredSize(new Dimension(112, 112));
        iconCard.setMaximumSize(new Dimension(112, 112));
        iconCard.setMinimumSize(new Dimension(112, 112));
        iconCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconCard.add(new JLabel(new VectorIcon(VectorIcon.Type.LOGO, 64)));

        JLabel title = new JLabel(LocalizationManager.getMessage("app.title"));
        title.setFont(font(Font.BOLD, 46));
        title.setForeground(PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = centeredHtml(
                LocalizationManager.getMessage("mainMenu.heroSubtitle"), MUTED, 14);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        hero.add(iconCard);
        hero.add(Box.createVerticalStrut(20));
        hero.add(title);
        hero.add(Box.createVerticalStrut(10));
        hero.add(subtitle);
        return hero;
    }

    private JPanel createMainRow() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 520));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;

        assembleCard = createAssembleCard();
        assembleCard.setPreferredSize(new Dimension(680, 440));
        assembleCard.setMinimumSize(new Dimension(560, 440));
        JPanel trade = createTradeColumn();
        trade.setPreferredSize(new Dimension(360, 440));
        trade.setMinimumSize(new Dimension(360, 440));

        c.gridx = 0;
        c.weightx = 0.65;
        c.insets = new Insets(0, 0, 0, 24);
        row.add(assembleCard, c);

        c.gridx = 1;
        c.weightx = 0.35;
        c.insets = new Insets(0, 0, 0, 0);
        row.add(trade, c);

        return row;
    }

    private JPanel createAssembleCard() {
        RoundedPanel card = new RoundedPanel(CARD_WHITE, 24);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 32, 32, 32));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));

        JPanel headText = new JPanel();
        headText.setLayout(new BoxLayout(headText, BoxLayout.Y_AXIS));
        headText.setOpaque(false);
        headText.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel h = new JLabel(LocalizationManager.getMessage("mainMenu.assembleTitle"));
        h.setFont(font(Font.BOLD, 20));
        h.setForeground(INK);
        h.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel(LocalizationManager.getMessage("mainMenu.assembleSubtitle"));
        sub.setFont(font(Font.PLAIN, 12));
        sub.setForeground(MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        headText.add(h);
        headText.add(Box.createVerticalStrut(4));
        headText.add(sub);

        pillGroup = new RoundedPanel(PILL_INACTIVE_BG, 24);
        pillGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));
        pillGroup.setAlignmentY(Component.TOP_ALIGNMENT);
        pillGroup.setMaximumSize(new Dimension(320, 40));
        populatePills();

        header.add(headText);
        header.add(Box.createHorizontalGlue());
        header.add(pillGroup);

        playersPanel = new JPanel();
        playersPanel.setOpaque(false);
        playersPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playersPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        rebuildPlayerCards();

        card.add(header);
        card.add(Box.createVerticalStrut(24));
        card.add(playersPanel);
        card.add(Box.createVerticalGlue());
        return card;
    }

    private void populatePills() {
        pillGroup.removeAll();
        for (int n : new int[]{2, 3, 4}) {
            boolean active = (n == selectedCount);
            JButton pill = new RoundedButton(
                    LocalizationManager.formatMessage("mainMenu.playerCount", n),
                    active ? EMERALD : PILL_INACTIVE_BG,
                    active ? Color.WHITE : MUTED, 20, null);
            pill.setFont(font(Font.BOLD, 10));
            pill.setPreferredSize(new Dimension(92, 30));
            final int count = n;
            pill.addActionListener(e -> {
                selectedCount = count;
                populatePills();
                rebuildPlayerCards();
            });
            pillGroup.add(pill);
        }
        pillGroup.revalidate();
        pillGroup.repaint();
    }

    private void rebuildPlayerCards() {
        List<String> previousNames = currentNameEntries();
        List<VectorIcon.Type> previousTokens = new ArrayList<>(selectedTokens);
        nameFields.clear();
        selectedTokens.clear();
        playersPanel.removeAll();
        int rows = (selectedCount == 4) ? 2 : 1;
        int cols = (selectedCount == 4) ? 2 : selectedCount;
        playersPanel.setLayout(new GridLayout(rows, cols, 20, 16));
        playersPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 220));
        for (int i = 0; i < selectedCount; i++) {
            selectedTokens.add(tokenAt(i, previousTokens));
            playersPanel.add(createPlayerCard(i));
        }
        restoreNameEntries(previousNames);
        if (assembleCard != null) {
            int h = (rows == 2) ? 600 : 440;
            assembleCard.setPreferredSize(new Dimension(680, h));
            assembleCard.setMinimumSize(new Dimension(560, h));
            assembleCard.revalidate();
        }
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private List<String> currentNameEntries() {
        List<String> names = new ArrayList<>();
        for (JTextField field : nameFields) {
            names.add(field.getText());
        }
        return names;
    }

    private VectorIcon.Type tokenAt(int index, List<VectorIcon.Type> previousTokens) {
        if (index < previousTokens.size()) {
            return previousTokens.get(index);
        }
        return TOKEN_SET[index % TOKEN_SET.length];
    }

    private void restoreNameEntries(List<String> names) {
        int count = Math.min(names.size(), nameFields.size());
        for (int i = 0; i < count; i++) {
            nameFields.get(i).setText(names.get(i));
        }
    }

    private JPanel createPlayerCard(int index) {
        RoundedPanel card = new RoundedPanel(PLAYER_CARD_BG, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(
                LocalizationManager.formatMessage("mainMenu.playerLabelPadded", index + 1));
        label.setFont(font(Font.BOLD, 12));
        label.setForeground(EMERALD);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedTextField field = new RoundedTextField(
                LocalizationManager.getMessage("mainMenu.enterNamePlaceholder"));
        field.setFont(font(Font.PLAIN, 14));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        nameFields.add(field);

        JLabel tokenLabel = new JLabel(LocalizationManager.getMessage("mainMenu.selectToken"));
        tokenLabel.setFont(font(Font.BOLD, 11));
        tokenLabel.setForeground(MUTED);
        tokenLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tokens = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tokens.setOpaque(false);
        tokens.setAlignmentX(Component.LEFT_ALIGNMENT);
        tokens.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        populateTokenRow(tokens, index);

        card.add(label);
        card.add(Box.createVerticalStrut(12));
        card.add(field);
        card.add(Box.createVerticalStrut(16));
        card.add(tokenLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(tokens);
        return card;
    }

    private void populateTokenRow(JPanel row, int index) {
        row.removeAll();
        for (VectorIcon.Type type : TOKEN_SET) {
            boolean selected = type == selectedTokens.get(index);
            JButton circle = createTokenCircle(type, selected);
            circle.addActionListener(e -> {
                selectedTokens.set(index, type);
                populateTokenRow(row, index);
                row.revalidate();
                row.repaint();
            });
            row.add(circle);
        }
    }

    private JButton createTokenCircle(VectorIcon.Type type, boolean selected) {
        JButton btn = new RoundedButton("", CARD_WHITE, MUTED, 44,
                selected ? EMERALD : FIELD_BORDER);
        btn.setIcon(new VectorIcon(type, 24));
        btn.setPreferredSize(new Dimension(46, 46));
        ((RoundedButton) btn).setBorderWidth(selected ? 2 : 1);
        return btn;
    }

    private JPanel createTradeColumn() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);

        RoundedPanel trade = new RoundedPanel(TRADE_GREEN, 24);
        trade.setLayout(new BoxLayout(trade, BoxLayout.Y_AXIS));
        trade.setBorder(new EmptyBorder(36, 32, 36, 32));
        trade.setAlignmentX(Component.LEFT_ALIGNMENT);
        trade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));

        JLabel title = new JLabel(LocalizationManager.getMessage("mainMenu.readyTitle"));
        title.setFont(font(Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc1 = new JLabel(LocalizationManager.getMessage("mainMenu.readyLineOne"));
        desc1.setFont(font(Font.PLAIN, 14));
        desc1.setForeground(TRADE_SUBTEXT);
        desc1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel desc2 = new JLabel(LocalizationManager.getMessage("mainMenu.readyLineTwo"));
        desc2.setFont(font(Font.PLAIN, 14));
        desc2.setForeground(TRADE_SUBTEXT);
        desc2.setAlignmentX(Component.LEFT_ALIGNMENT);

        newGameButton = new RoundedButton(
                LocalizationManager.getMessage("mainMenu.startButton"),
                Color.WHITE, TRADE_GREEN, 28, null);
        newGameButton.setFont(font(Font.BOLD, 17));
        newGameButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        newGameButton.setPreferredSize(new Dimension(260, 56));

        trade.add(title);
        trade.add(Box.createVerticalStrut(14));
        trade.add(desc1);
        trade.add(Box.createVerticalStrut(2));
        trade.add(desc2);
        trade.add(Box.createVerticalStrut(36));
        trade.add(newGameButton);
        trade.add(Box.createVerticalGlue());

        col.add(trade);
        col.add(Box.createVerticalGlue());
        return col;
    }

    private JPanel createShowcaseRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 24, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        Object[][] items = {
                {VectorIcon.Type.CAR, LocalizationManager.getMessage("mainMenu.token.car")},
                {VectorIcon.Type.BOAT, LocalizationManager.getMessage("mainMenu.token.boat")},
                {VectorIcon.Type.IRON, LocalizationManager.getMessage("mainMenu.token.iron")},
                {VectorIcon.Type.HAT, LocalizationManager.getMessage("mainMenu.token.hat")},
        };
        for (Object[] item : items) {
            RoundedPanel cell = new RoundedPanel(new Color(0xF3, 0xF8, 0xF3), 20);
            cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
            cell.setBorder(new EmptyBorder(28, 20, 24, 20));

            JLabel icon = new JLabel(new VectorIcon((VectorIcon.Type) item[0], 84));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel name = new JLabel((String) item[1]);
            name.setFont(font(Font.BOLD, 12));
            name.setForeground(MUTED);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            cell.add(icon);
            cell.add(Box.createVerticalStrut(16));
            cell.add(name);
            row.add(cell);
        }
        return row;
    }

    private static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    private static JLabel centeredHtml(String text, Color color, int size) {
        JLabel label = new JLabel("<html><div style='text-align:center;width:520px'>"
                + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(font(Font.PLAIN, size));
        label.setForeground(color);
        return label;
    }

    private static class ScrollableWidthPanel extends JPanel implements Scrollable {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle r, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle r, int orientation, int direction) {
            return r.height;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
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
        private int borderWidth = 1;

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
            setHorizontalTextPosition(SwingConstants.CENTER);
        }

        void setBorderWidth(int width) {
            this.borderWidth = width;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            if (getModel().isPressed()) {
                g2.setColor(bg.darker());
            } else {
                g2.setColor(bg);
            }
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            if (border != null) {
                g2.setColor(border);
                g2.setStroke(new BasicStroke(borderWidth));
                g2.drawRoundRect(borderWidth / 2, borderWidth / 2,
                        w - borderWidth - 1, h - borderWidth - 1, arc, arc);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RoundedTextField extends JTextField {
        private final String placeholder;

        RoundedTextField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new EmptyBorder(8, 14, 8, 14));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, w - 1, h - 1, 12, 12);
            g2.setColor(FIELD_BORDER);
            g2.drawRoundRect(0, 0, w - 1, h - 1, 12, 12);
            g2.dispose();
            super.paintComponent(g);

            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D gp = (Graphics2D) g.create();
                gp.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                gp.setColor(new Color(0x9A, 0xA3, 0xA0));
                gp.setFont(getFont());
                Insets ins = getInsets();
                FontMetrics fm = gp.getFontMetrics();
                gp.drawString(placeholder, ins.left,
                        (h - fm.getHeight()) / 2 + fm.getAscent());
                gp.dispose();
            }
        }
    }

    private static class VectorIcon implements Icon {
        enum Type { LOGO, CAR, BOAT, HAT, IRON }

        private static final Color TOKEN_LIGHT = new Color(0xCE, 0xD5, 0xD1);
        private static final Color TOKEN_MID = new Color(0xA6, 0xAF, 0xAA);
        private static final Color TOKEN_DARK = new Color(0x7C, 0x86, 0x80);

        private final Type type;
        private final int size;

        VectorIcon(Type type, int size) {
            this.type = type;
            this.size = size;
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
            double s = size;
            switch (type) {
                case LOGO:
                    paintLogo(g2, s);
                    break;
                case CAR:
                    paintCar(g2, s);
                    break;
                case BOAT:
                    paintBoat(g2, s);
                    break;
                case HAT:
                    paintHat(g2, s);
                    break;
                case IRON:
                    paintIron(g2, s);
                    break;
                default:
                    break;
            }
            g2.dispose();
        }

        private void paintLogo(Graphics2D g2, double s) {
            g2.setColor(EMERALD);
            g2.fill(new RoundRectangle2D.Double(0, 0, s, s, s * 0.28, s * 0.28));

            double pad = s * 0.18;
            double gap = s * 0.08;
            double tile = (s - 2 * pad - gap) / 2;
            double r = tile * 0.35;
            Color tileColor = new Color(0x4E, 0xDE, 0xA3);
            double[][] pos = {
                    {pad, pad}, {pad + tile + gap, pad},
                    {pad, pad + tile + gap}, {pad + tile + gap, pad + tile + gap}
            };
            for (int i = 0; i < 3; i++) {
                g2.setColor(tileColor);
                g2.fill(new RoundRectangle2D.Double(pos[i][0], pos[i][1], tile, tile, r, r));
            }
            double hx = pos[3][0];
            double hy = pos[3][1];
            g2.setColor(tileColor);
            g2.fill(new RoundRectangle2D.Double(hx, hy, tile, tile, r, r));
            g2.setColor(Color.WHITE);
            GeneralPath roof = new GeneralPath();
            roof.moveTo(hx + tile * 0.5, hy + tile * 0.22);
            roof.lineTo(hx + tile * 0.8, hy + tile * 0.5);
            roof.lineTo(hx + tile * 0.2, hy + tile * 0.5);
            roof.closePath();
            g2.fill(roof);
            g2.fill(new RoundRectangle2D.Double(hx + tile * 0.3, hy + tile * 0.5,
                    tile * 0.4, tile * 0.28, tile * 0.06, tile * 0.06));
        }

        private void paintCar(Graphics2D g2, double s) {
            g2.setPaint(new GradientPaint(0, 0, TOKEN_LIGHT, 0, (float) s, TOKEN_MID));
            g2.fill(new RoundRectangle2D.Double(s * 0.22, s * 0.30, s * 0.46, s * 0.24,
                    s * 0.12, s * 0.12));
            g2.fill(new RoundRectangle2D.Double(s * 0.06, s * 0.46, s * 0.88, s * 0.26,
                    s * 0.16, s * 0.16));
            g2.setColor(TOKEN_DARK);
            double wr = s * 0.12;
            g2.fill(new Ellipse2D.Double(s * 0.20 - wr, s * 0.66, wr * 2, wr * 2));
            g2.fill(new Ellipse2D.Double(s * 0.74 - wr, s * 0.66, wr * 2, wr * 2));
            g2.setColor(TOKEN_LIGHT);
            double ir = wr * 0.45;
            g2.fill(new Ellipse2D.Double(s * 0.20 - ir, s * 0.66 + (wr - ir), ir * 2, ir * 2));
            g2.fill(new Ellipse2D.Double(s * 0.74 - ir, s * 0.66 + (wr - ir), ir * 2, ir * 2));
        }

        private void paintBoat(Graphics2D g2, double s) {
            g2.setPaint(new GradientPaint(0, 0, TOKEN_LIGHT, 0, (float) s, TOKEN_MID));
            GeneralPath sail = new GeneralPath();
            sail.moveTo(s * 0.50, s * 0.08);
            sail.lineTo(s * 0.50, s * 0.60);
            sail.lineTo(s * 0.84, s * 0.60);
            sail.closePath();
            g2.fill(sail);
            g2.setColor(TOKEN_DARK);
            g2.setStroke(new BasicStroke((float) (s * 0.04), BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));
            g2.drawLine((int) (s * 0.46), (int) (s * 0.08), (int) (s * 0.46), (int) (s * 0.62));
            g2.setPaint(new GradientPaint(0, (float) (s * 0.6), TOKEN_MID, 0, (float) s, TOKEN_DARK));
            GeneralPath hull = new GeneralPath();
            hull.moveTo(s * 0.12, s * 0.64);
            hull.lineTo(s * 0.88, s * 0.64);
            hull.lineTo(s * 0.74, s * 0.84);
            hull.lineTo(s * 0.26, s * 0.84);
            hull.closePath();
            g2.fill(hull);
        }

        private void paintHat(Graphics2D g2, double s) {
            g2.setPaint(new GradientPaint(0, 0, TOKEN_MID, 0, (float) s, TOKEN_DARK));
            g2.fill(new Ellipse2D.Double(s * 0.08, s * 0.66, s * 0.84, s * 0.18));
            g2.setPaint(new GradientPaint(0, 0, TOKEN_LIGHT, 0, (float) s, TOKEN_MID));
            g2.fill(new RoundRectangle2D.Double(s * 0.28, s * 0.16, s * 0.44, s * 0.56,
                    s * 0.06, s * 0.06));
            g2.setColor(TOKEN_DARK);
            g2.fill(new RoundRectangle2D.Double(s * 0.28, s * 0.56, s * 0.44, s * 0.10,
                    s * 0.04, s * 0.04));
        }

        private void paintIron(Graphics2D g2, double s) {
            g2.setColor(TOKEN_DARK);
            g2.setStroke(new BasicStroke((float) (s * 0.09), BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));
            GeneralPath handle = new GeneralPath();
            handle.moveTo(s * 0.24, s * 0.50);
            handle.curveTo(s * 0.24, s * 0.22, s * 0.66, s * 0.22, s * 0.66, s * 0.50);
            g2.draw(handle);
            g2.setPaint(new GradientPaint(0, (float) (s * 0.45), TOKEN_LIGHT,
                    0, (float) s, TOKEN_MID));
            GeneralPath body = new GeneralPath();
            body.moveTo(s * 0.16, s * 0.50);
            body.lineTo(s * 0.74, s * 0.50);
            body.quadTo(s * 0.96, s * 0.52, s * 0.92, s * 0.66);
            body.lineTo(s * 0.22, s * 0.72);
            body.quadTo(s * 0.12, s * 0.70, s * 0.12, s * 0.58);
            body.closePath();
            g2.fill(body);
            g2.setColor(TOKEN_DARK);
            g2.fill(new RoundRectangle2D.Double(s * 0.16, s * 0.70, s * 0.70, s * 0.06,
                    s * 0.03, s * 0.03));
        }
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "MainMenuView exposes live Swing controls for view wiring and UI tests.")
    public JButton getNewGameButton() {
        return newGameButton;
    }

    public List<JTextField> getNameFields() {
        return new ArrayList<>(nameFields);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuView().setVisible(true));
    }
}
