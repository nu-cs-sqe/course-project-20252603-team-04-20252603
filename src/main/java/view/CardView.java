package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Card;
import util.LocalizationManager;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.Map;

public class CardView {

    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color PRIMARY = new Color(0x00, 0x6C, 0x49);
    private static final Map<String, String> TITLE_KEYS = Map.of(
            "Advance to GO", "chance.advanceToGo.title",
            "Go to Jail", "chance.goToJail.title",
            "Go Back 3 Spaces", "chance.goBackThreeSpaces.title",
            "AI Bubble Pops", "chance.aiBubblePops.title",
            "Subscription Service", "chance.subscriptionService.title",
            "Stock Market Crash", "chance.stockMarketCrash.title");
    private static final Map<String, String> DESCRIPTION_KEYS = Map.of(
            "Advance to GO. Collect $200.", "chance.advanceToGo.description",
            "Go directly to Jail.", "chance.goToJail.description",
            "Move back three spaces.", "chance.goBackThreeSpaces.description",
            "The AI bubble pops: you lose $500.", "chance.aiBubblePops.description",
            "Pay $100 for a subscription service.", "chance.subscriptionService.description",
            "Stock market crashes: every player loses $200.",
            "chance.stockMarketCrash.description");

    private final Frame owner;
    private JDialog dialog;
    private ActionListener proceedListener;

    public CardView() {
        this(null);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The Swing parent frame is shared by reference solely for dialog ownership.")
    public CardView(Frame owner) {
        this.owner = owner;
    }

    public void showCard(Card card) {
        if (dialog == null) {
            dialog = new JDialog(owner, LocalizationManager.getMessage("card.title"), false);
            dialog.setSize(380, 220);
        }
        dialog.setTitle(LocalizationManager.getMessage("card.title"));
        dialog.setLocationRelativeTo(owner);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setBorder(new EmptyBorder(20, 24, 16, 24));

        JLabel title = new JLabel(localizedText(card.getTitle(), TITLE_KEYS), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY);

        JLabel description = new JLabel(
                "<html><div style='text-align:center'>"
                        + localizedText(card.getDescription(), DESCRIPTION_KEYS)
                        + "</div></html>",
                SwingConstants.CENTER);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(INK);

        JButton proceed = new JButton(LocalizationManager.getMessage("card.proceedButton"));
        proceed.setForeground(PRIMARY);
        if (proceedListener != null) {
            proceed.addActionListener(proceedListener);
        }
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttons.add(proceed);

        body.add(title, BorderLayout.NORTH);
        body.add(description, BorderLayout.CENTER);
        body.add(buttons, BorderLayout.SOUTH);
        dialog.setContentPane(body);
        dialog.revalidate();
        dialog.setVisible(true);
    }

    private static String localizedText(String text, Map<String, String> keys) {
        String key = keys.get(text);
        if (key == null) {
            return text;
        }
        return LocalizationManager.getMessage(key);
    }

    public void setProceedListener(ActionListener listener) {
        this.proceedListener = listener;
    }

    public void close() {
        if (dialog != null) {
            dialog.dispose();
            dialog = null;
        }
    }
}
