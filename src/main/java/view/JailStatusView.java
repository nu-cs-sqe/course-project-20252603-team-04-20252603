package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Player;
import util.LocalizationManager;

import javax.swing.JOptionPane;
import java.awt.Frame;
import java.util.Objects;

public class JailStatusView {

    private final Frame owner;

    public JailStatusView() {
        this(null);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The Swing parent frame is shared by reference solely for dialog ownership.")
    public JailStatusView(Frame owner) {
        this.owner = owner;
    }

    public void showStillInJail(Player player, int turnsRemaining) {
        Objects.requireNonNull(player, "Player cannot be null");

        JOptionPane.showMessageDialog(
                owner,
                LocalizationManager.formatMessage(
                        "jailStatus.stillInJailMessage",
                        player.getName(),
                        turnsRemaining),
                LocalizationManager.getMessage("jailStatus.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }
}
