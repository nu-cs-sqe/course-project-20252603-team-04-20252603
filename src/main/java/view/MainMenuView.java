package view;

import util.Constants;
import util.LocalizationManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainMenuView {
    private static final int FRAME_WIDTH = 520;
    private static final int FRAME_HEIGHT = 460;
    private static final int ICON_SIZE = 48;
    private static final int PANEL_GAP = 12;
    private static final int FIELD_COLUMNS = 16;

    private final JFrame frame;
    private final JComboBox<Integer> playerCountSelector;
    private final JComboBox<LocaleOption> languageSelector;
    private final JPanel playerRowsPanel;
    private final JButton startButton;
    private final JLabel titleLabel;
    private final JLabel playersLabel;
    private final JLabel languageLabel;
    private final List<JTextField> nameFields;
    private final List<JButton> iconButtons;
    private final List<ImageIcon> selectedIcons;
    private final List<LocaleOption> localeOptions;

    public MainMenuView() {
        frame = createFrame();
        playerCountSelector = createPlayerCountSelector();
        localeOptions = createLocaleOptions();
        languageSelector = createLanguageSelector();
        playerRowsPanel = new JPanel(new GridBagLayout());
        startButton = new JButton();
        titleLabel = createTitleLabel();
        playersLabel = new JLabel();
        languageLabel = new JLabel();
        nameFields = new ArrayList<>();
        iconButtons = new ArrayList<>();
        selectedIcons = new ArrayList<>();

        frame.setContentPane(createContentPanel());
        refreshText();
        updatePlayerRows();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public int getPlayerCount() {
        Integer selectedCount = (Integer) playerCountSelector.getSelectedItem();
        if (selectedCount == null) {
            return Constants.MIN_NUM_PLAYERS;
        }
        return selectedCount;
    }

    public List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        int playerCount = getPlayerCount();

        for (int index = 0; index < playerCount; index++) {
            playerNames.add(nameFields.get(index).getText().trim());
        }

        return playerNames;
    }

    public List<ImageIcon> getPlayerIcons() {
        List<ImageIcon> playerIcons = new ArrayList<>();
        int playerCount = getPlayerCount();

        for (int index = 0; index < playerCount; index++) {
            playerIcons.add(copyIcon(selectedIcons.get(index)));
        }

        return playerIcons;
    }

    public void setStartGameListener(ActionListener listener) {
        for (ActionListener existingListener : startButton.getActionListeners()) {
            startButton.removeActionListener(existingListener);
        }
        startButton.addActionListener(listener);
    }

    private JFrame createFrame() {
        JFrame menuFrame = new JFrame(LocalizationManager.getMessage("mainMenu.title"));

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        menuFrame.setResizable(false);

        return menuFrame;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(PANEL_GAP, PANEL_GAP));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_GAP, PANEL_GAP, PANEL_GAP, PANEL_GAP));

        contentPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        contentPanel.add(playerRowsPanel, BorderLayout.CENTER);
        contentPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JPanel playerCountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerCountPanel.add(playersLabel);
        playerCountPanel.add(playerCountSelector);

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        languagePanel.add(languageLabel);
        languagePanel.add(languageSelector);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(PANEL_GAP));
        headerPanel.add(playerCountPanel);
        headerPanel.add(languagePanel);

        return headerPanel;
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);

        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 28.0f));

        return label;
    }

    private JComboBox<Integer> createPlayerCountSelector() {
        JComboBox<Integer> selector = new JComboBox<>();

        for (int count = Constants.MIN_NUM_PLAYERS; count <= Constants.MAX_NUM_PLAYERS; count++) {
            selector.addItem(count);
        }

        selector.addActionListener(event -> updatePlayerRows());
        return selector;
    }

    private JComboBox<LocaleOption> createLanguageSelector() {
        JComboBox<LocaleOption> selector = new JComboBox<>(localeOptions.toArray(new LocaleOption[0]));

        refreshLocaleOptionNames();
        selector.setSelectedItem(findLocaleOption(LocalizationManager.getLocale()));
        selector.addActionListener(event -> changeLanguage());

        return selector;
    }

    private List<LocaleOption> createLocaleOptions() {
        List<LocaleOption> options = new ArrayList<>();

        for (Locale locale : LocalizationManager.getSupportedLocales()) {
            options.add(new LocaleOption(locale, getLocaleMessageKey(locale)));
        }

        return options;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(startButton);
        return footerPanel;
    }

    private void updatePlayerRows() {
        ensurePlayerInputs();
        playerRowsPanel.removeAll();

        int playerCount = getPlayerCount();
        for (int index = 0; index < playerCount; index++) {
            addPlayerRow(index);
        }

        playerRowsPanel.revalidate();
        playerRowsPanel.repaint();
    }

    private void ensurePlayerInputs() {
        while (nameFields.size() < Constants.MAX_NUM_PLAYERS) {
            int playerIndex = nameFields.size();
            nameFields.add(new JTextField(
                    LocalizationManager.formatMessage("mainMenu.defaultPlayerName", playerIndex + 1),
                    FIELD_COLUMNS
            ));

            ImageIcon defaultIcon = createDefaultIcon(playerIndex);
            selectedIcons.add(defaultIcon);
            iconButtons.add(createIconButton(playerIndex, defaultIcon));
        }
    }

    private JButton createIconButton(int playerIndex, ImageIcon icon) {
        JButton iconButton = new JButton(copyIcon(icon));

        iconButton.setPreferredSize(new Dimension(ICON_SIZE + PANEL_GAP, ICON_SIZE + PANEL_GAP));
        iconButton.setToolTipText(LocalizationManager.formatMessage("mainMenu.chooseIconTooltip", playerIndex + 1));
        iconButton.addActionListener(event -> chooseIcon(playerIndex));

        return iconButton;
    }

    private void addPlayerRow(int playerIndex) {
        GridBagConstraints constraints = createRowConstraints(playerIndex);

        constraints.gridx = 0;
        playerRowsPanel.add(
                new JLabel(LocalizationManager.formatMessage("mainMenu.playerLabel", playerIndex + 1)),
                constraints
        );

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        playerRowsPanel.add(nameFields.get(playerIndex), constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        playerRowsPanel.add(iconButtons.get(playerIndex), constraints);
    }

    private GridBagConstraints createRowConstraints(int playerIndex) {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = playerIndex;
        constraints.insets = new Insets(PANEL_GAP / 2, PANEL_GAP / 2, PANEL_GAP / 2, PANEL_GAP / 2);
        constraints.anchor = GridBagConstraints.CENTER;

        return constraints;
    }

    private void chooseIcon(int playerIndex) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(LocalizationManager.getMessage("mainMenu.chooseIconDialogTitle"));
        fileChooser.setFileFilter(
                new FileNameExtensionFilter(
                        LocalizationManager.getMessage("mainMenu.imageFileFilter"),
                        "png",
                        "jpg",
                        "jpeg",
                        "gif"
                )
        );

        int result = fileChooser.showOpenDialog(frame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        updateIcon(playerIndex, fileChooser.getSelectedFile());
    }

    private void updateIcon(int playerIndex, File iconFile) {
        ImageIcon icon = new ImageIcon(iconFile.getAbsolutePath());

        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
            JOptionPane.showMessageDialog(frame, LocalizationManager.getMessage("mainMenu.invalidIconMessage"));
            return;
        }

        ImageIcon scaledIcon = scaleIcon(icon);
        selectedIcons.set(playerIndex, scaledIcon);
        iconButtons.get(playerIndex).setIcon(copyIcon(scaledIcon));
    }

    private ImageIcon createDefaultIcon(int playerIndex) {
        Color[] colors = {
                new Color(0xD7263D),
                new Color(0x1B998B),
                new Color(0xF4A261),
                new Color(0x2E86AB)
        };
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(colors[playerIndex % colors.length]);
        graphics.fillOval(2, 2, ICON_SIZE - 4, ICON_SIZE - 4);
        graphics.setColor(Color.WHITE);
        graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 20.0f));
        graphics.drawString(String.valueOf(playerIndex + 1), ICON_SIZE / 2 - 6, ICON_SIZE / 2 + 7);
        graphics.dispose();

        return new ImageIcon(image);
    }

    private ImageIcon scaleIcon(ImageIcon icon) {
        Image scaledImage = icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon copyIcon(ImageIcon icon) {
        return new ImageIcon(icon.getImage());
    }

    private void changeLanguage() {
        LocaleOption selectedOption = (LocaleOption) languageSelector.getSelectedItem();
        if (selectedOption == null || selectedOption.getLocale().equals(LocalizationManager.getLocale())) {
            return;
        }

        List<Boolean> defaultNameFields = findDefaultNameFields();
        LocalizationManager.setLocale(selectedOption.getLocale());

        updateDefaultPlayerNames(defaultNameFields);
        refreshText();
        updatePlayerRows();
        frame.pack();
    }

    private void refreshText() {
        frame.setTitle(LocalizationManager.getMessage("mainMenu.title"));
        titleLabel.setText(LocalizationManager.getMessage("mainMenu.title"));
        playersLabel.setText(LocalizationManager.getMessage("mainMenu.playersLabel"));
        languageLabel.setText(LocalizationManager.getMessage("mainMenu.languageLabel"));
        startButton.setText(LocalizationManager.getMessage("mainMenu.startButton"));
        refreshLanguageOptions();
        refreshIconToolTips();
    }

    private void refreshLanguageOptions() {
        LocaleOption selectedOption = (LocaleOption) languageSelector.getSelectedItem();

        refreshLocaleOptionNames();

        if (selectedOption != null) {
            languageSelector.setSelectedItem(selectedOption);
        }
        languageSelector.repaint();
    }

    private void refreshLocaleOptionNames() {
        for (LocaleOption localeOption : localeOptions) {
            localeOption.setDisplayName(LocalizationManager.getMessage(localeOption.getMessageKey()));
        }
    }

    private void refreshIconToolTips() {
        for (int index = 0; index < iconButtons.size(); index++) {
            iconButtons.get(index).setToolTipText(
                    LocalizationManager.formatMessage("mainMenu.chooseIconTooltip", index + 1)
            );
        }
    }

    private List<Boolean> findDefaultNameFields() {
        List<Boolean> defaultNameFields = new ArrayList<>();

        for (int index = 0; index < nameFields.size(); index++) {
            JTextField nameField = nameFields.get(index);
            String defaultName = LocalizationManager.formatMessage("mainMenu.defaultPlayerName", index + 1);

            defaultNameFields.add(nameField.getText().equals(defaultName));
        }

        return defaultNameFields;
    }

    private void updateDefaultPlayerNames(List<Boolean> defaultNameFields) {
        for (int index = 0; index < defaultNameFields.size(); index++) {
            if (defaultNameFields.get(index)) {
                JTextField nameField = nameFields.get(index);
                nameField.setText(LocalizationManager.formatMessage("mainMenu.defaultPlayerName", index + 1));
            }
        }
    }

    private LocaleOption findLocaleOption(Locale locale) {
        for (LocaleOption localeOption : localeOptions) {
            if (localeOption.getLocale().equals(locale)) {
                return localeOption;
            }
        }

        return localeOptions.get(0);
    }

    private String getLocaleMessageKey(Locale locale) {
        if (LocalizationManager.SPANISH.getLanguage().equals(locale.getLanguage())) {
            return "mainMenu.language.spanish";
        }

        return "mainMenu.language.english";
    }

    private static class LocaleOption {
        private final Locale locale;
        private final String messageKey;
        private String displayName;

        LocaleOption(Locale locale, String messageKey) {
            this.locale = locale;
            this.messageKey = messageKey;
            displayName = "";
        }

        Locale getLocale() {
            return locale;
        }

        String getMessageKey() {
            return messageKey;
        }

        void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
