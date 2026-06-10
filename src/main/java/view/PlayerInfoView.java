package view;

import model.Player;
import model.Property;
import util.LocalizationManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PlayerInfoView extends JPanel {

    private static final Color CARD_WHITE = Color.WHITE;
    private static final Color EMERALD = new Color(0x10, 0xB9, 0x81);
    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color MUTED = new Color(0x5C, 0x66, 0x60);
    private static final Color PANEL_BG = new Color(0xF4, 0xFB, 0xF4);
    private static final Color SELECTED_BG = new Color(0xED, 0xF4, 0xEE);
    private static final Color BORDER = new Color(0xD8, 0xE2, 0xDB);

    private static final String FONT_FAMILY = "Segoe UI";
    private static final int VIEW_WIDTH = 218;
    private static final int MIN_HEIGHT = 180;
    private static final int MAX_HEIGHT = 360;
    private static final int CONTENT_GAP = 10;

    private final JLabel currentTurnLabel;
    private final JPanel playersPanel;
    private final JScrollPane scrollPane;
    private final Map<Player, PlayerCard> playerCards;

    public PlayerInfoView() {
        currentTurnLabel = new JLabel(LocalizationManager.getMessage("playerInfo.noCurrentTurn"), SwingConstants.LEFT);
        playersPanel = new JPanel();
        playerCards = new HashMap<>();

        setLayout(new BorderLayout(0, 10));
        setBackground(PANEL_BG);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMinimumSize(new Dimension(VIEW_WIDTH, MIN_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, MIN_HEIGHT));
        setPreferredSize(new Dimension(VIEW_WIDTH, MIN_HEIGHT));

        currentTurnLabel.setFont(font(Font.BOLD, 13));
        currentTurnLabel.setForeground(INK);

        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBackground(PANEL_BG);

        scrollPane = new JScrollPane(
                playersPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(VIEW_WIDTH, MIN_HEIGHT));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        add(currentTurnLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void renderPlayers(List<Player> players) {
        Objects.requireNonNull(players, "Players cannot be null");

        playersPanel.removeAll();
        playerCards.clear();

        if (players.isEmpty()) {
            addEmptyState();
            refresh();
            return;
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = Objects.requireNonNull(players.get(i), "Player cannot be null");
            PlayerCard card = createPlayerCard(player);
            playerCards.put(player, card);
            playersPanel.add(card.panel);
            if (i < players.size() - 1) {
                playersPanel.add(Box.createVerticalStrut(8));
            }
        }
        refresh();
    }

    public void updateBalance(Player player) {
        PlayerCard card = playerCards.get(player);
        if (card == null) {
            return;
        }
        card.balanceLabel.setText(LocalizationManager.formatMessage(
                "playerInfo.balance",
                formatMoney(player.getBalance())));
    }

    public void updateProperties(Player player) {
        PlayerCard card = playerCards.get(player);
        if (card == null) {
            return;
        }
        renderProperties(player, card);
    }

    public void showCurrentTurn(Player player) {
        Objects.requireNonNull(player, "Player cannot be null");
        currentTurnLabel.setText(LocalizationManager.formatMessage("playerInfo.currentTurn", player.getName()));

        for (Map.Entry<Player, PlayerCard> entry : playerCards.entrySet()) {
            boolean currentPlayer = entry.getKey().equals(player);
            styleCard(entry.getValue().panel, currentPlayer);
            resizeCard(entry.getValue());
        }
        refresh();
    }

    private PlayerCard createPlayerCard(Player player) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        styleCard(cardPanel, false);

        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(font(Font.BOLD, 13));
        nameLabel.setForeground(INK);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel balanceLabel = new JLabel(LocalizationManager.formatMessage(
                "playerInfo.balance",
                formatMoney(player.getBalance())));
        balanceLabel.setFont(font(Font.PLAIN, 12));
        balanceLabel.setForeground(EMERALD);
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel propertyCountLabel = new JLabel();
        propertyCountLabel.setFont(font(Font.PLAIN, 12));
        propertyCountLabel.setForeground(MUTED);
        propertyCountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
        propertiesPanel.setOpaque(false);
        propertiesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        PlayerCard card = new PlayerCard(cardPanel, balanceLabel, propertyCountLabel, propertiesPanel);
        cardPanel.add(nameLabel);
        cardPanel.add(Box.createVerticalStrut(4));
        cardPanel.add(balanceLabel);
        cardPanel.add(Box.createVerticalStrut(2));
        cardPanel.add(propertyCountLabel);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(propertiesPanel);

        renderProperties(player, card);
        return card;
    }

    private void renderProperties(Player player, PlayerCard card) {
        List<Property> properties = new ArrayList<>(player.getOwnedProperties());
        properties.sort(Comparator.comparing(Property::getPropertyName));

        card.propertyCountLabel.setText(LocalizationManager.formatMessage(
                "playerInfo.properties",
                properties.size()));
        card.propertiesPanel.removeAll();

        if (properties.isEmpty()) {
            card.propertiesPanel.add(propertyLabel(LocalizationManager.getMessage("playerInfo.noProperties")));
            resizeCard(card);
            refresh();
            return;
        }

        for (Property property : properties) {
            String text = LocalizationManager.formatMessage(
                    "playerInfo.propertyLine",
                    property.getPropertyName(),
                    formatMoney(property.getPrice()));
            card.propertiesPanel.add(propertyLabel(text));
        }
        resizeCard(card);
        refresh();
    }

    private JLabel propertyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(font(Font.PLAIN, 11));
        label.setForeground(MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void addEmptyState() {
        JLabel emptyLabel = new JLabel(LocalizationManager.getMessage("playerInfo.noActivePlayers"));
        emptyLabel.setFont(font(Font.PLAIN, 12));
        emptyLabel.setForeground(MUTED);
        emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playersPanel.add(emptyLabel);
    }

    private void styleCard(JPanel panel, boolean selected) {
        panel.setBackground(selected ? SELECTED_BG : CARD_WHITE);

        Border line = new LineBorder(selected ? EMERALD : BORDER, selected ? 2 : 1, true);
        panel.setBorder(new CompoundBorder(line, new EmptyBorder(10, 12, 10, 12)));
    }

    private void refresh() {
        updatePreferredHeight();
        playersPanel.revalidate();
        playersPanel.repaint();
        revalidate();
        repaint();
        Container parent = getParent();
        if (parent != null) {
            parent.revalidate();
            parent.repaint();
        }
    }

    private void updatePreferredHeight() {
        int contentHeight = playersPanel.getPreferredSize().height;
        int headerHeight = currentTurnLabel.getPreferredSize().height;
        int preferredHeight = Math.max(MIN_HEIGHT, contentHeight + headerHeight + CONTENT_GAP);
        int height = Math.min(preferredHeight, MAX_HEIGHT);
        setPreferredSize(new Dimension(VIEW_WIDTH, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        scrollPane.setPreferredSize(new Dimension(VIEW_WIDTH, Math.max(0, height - headerHeight - CONTENT_GAP)));
    }

    private void resizeCard(PlayerCard card) {
        Dimension preferredSize = card.panel.getPreferredSize();
        card.panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
    }

    private static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    private static String formatMoney(double amount) {
        return String.format(Locale.US, "$%,d", (int) amount);
    }

    private static final class PlayerCard {
        private final JPanel panel;
        private final JLabel balanceLabel;
        private final JLabel propertyCountLabel;
        private final JPanel propertiesPanel;

        private PlayerCard(
                JPanel panel,
                JLabel balanceLabel,
                JLabel propertyCountLabel,
                JPanel propertiesPanel) {
            this.panel = panel;
            this.balanceLabel = balanceLabel;
            this.propertyCountLabel = propertyCountLabel;
            this.propertiesPanel = propertiesPanel;
        }
    }
}
