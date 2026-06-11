package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import util.LocalizationManager;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class DiceView {

    private final JLabel label;
    private JButton rollButton;

    public DiceView() {
        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(0x16, 0x1D, 0x19));
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Exposes the label component so the board can host the dice readout.")
    public JComponent getComponent() {
        return label;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "DiceView controls the live roll button supplied by BoardView.")
    public void setRollButton(JButton rollButton) {
        this.rollButton = rollButton;
    }

    public void showRollResult(int dieOne, int dieTwo) {
        label.setText(LocalizationManager.formatMessage("dice.rollResult", dieOne, dieTwo, dieOne + dieTwo));
    }

    public void enableRollButton() {
        if (rollButton != null) {
            rollButton.setEnabled(true);
        }
    }

    public void disableRollButton() {
        if (rollButton != null) {
            rollButton.setEnabled(false);
        }
    }
}
