package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Player;
import model.Property;
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
import java.util.Objects;

public class PropertyPromptView {

    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color EMERALD = new Color(0x10, 0xB9, 0x81);

    private final Frame owner;
    private JDialog dialog;
    private JButton buyButton;
    private JButton declineButton;
    private ActionListener buyListener;
    private ActionListener declineListener;

    public PropertyPromptView() {
        this(null);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The Swing parent frame is shared by reference solely for dialog ownership.")
    public PropertyPromptView(Frame owner) {
        this.owner = owner;
    }

    public void showProperty(Property property, Player player) {
        dialog = new JDialog(owner, LocalizationManager.getMessage("propertyPrompt.title"), false);
        dialog.setSize(360, 260);
        dialog.setLocationRelativeTo(owner);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setBorder(new EmptyBorder(20, 24, 16, 24));

        JLabel title = new JLabel(property.getPropertyName(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(INK);

        String detail = LocalizationManager.formatMessage(
                "propertyPrompt.detailHtml",
                formatMoney(property.getPrice()),
                formatMoney(property.getRent()),
                player.getName());
        JLabel info = new JLabel(detail, SwingConstants.CENTER);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buyButton = new JButton(LocalizationManager.formatMessage(
                "propertyPrompt.buyButton",
                formatMoney(property.getPrice())));
        buyButton.setForeground(EMERALD);
        declineButton = new JButton(LocalizationManager.getMessage("propertyPrompt.declineButton"));
        attachButtonListeners();
        buttons.add(buyButton);
        buttons.add(declineButton);

        body.add(title, BorderLayout.NORTH);
        body.add(info, BorderLayout.CENTER);
        body.add(buttons, BorderLayout.SOUTH);
        dialog.setContentPane(body);
        dialog.setVisible(true);
    }

    public void setBuyListener(ActionListener listener) {
        buyListener = Objects.requireNonNull(listener, "Buy listener cannot be null");
        if (buyButton != null) {
            attachBuyListener();
        }
    }

    public void setDeclineListener(ActionListener listener) {
        declineListener = Objects.requireNonNull(listener, "Decline listener cannot be null");
        if (declineButton != null) {
            attachDeclineListener();
        }
    }

    public void close() {
        if (dialog != null) {
            dialog.dispose();
            dialog = null;
        }
        buyButton = null;
        declineButton = null;
    }

    private void attachButtonListeners() {
        attachBuyListener();
        attachDeclineListener();
    }

    private void attachBuyListener() {
        if (buyButton != null && buyListener != null) {
            buyButton.addActionListener(event -> {
                close();
                buyListener.actionPerformed(event);
            });
        }
    }

    private void attachDeclineListener() {
        if (declineButton != null && declineListener != null) {
            declineButton.addActionListener(event -> {
                close();
                declineListener.actionPerformed(event);
            });
        }
    }

    private static String formatMoney(double amount) {
        return String.format(java.util.Locale.US, "$%,d", (int) amount);
    }
}
