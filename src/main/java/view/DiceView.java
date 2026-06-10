package view;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

/**
 * Passive dice readout. The board hosts {@link #getComponent()} and the controller calls
 * {@link #showRollResult(int, int)} after each roll. Enable/disable are no-ops; the Roll button on
 * the board stays available between turns.
 */
public class DiceView {

    private final JLabel label;

    public DiceView() {
        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(0x16, 0x1D, 0x19));
    }

    public JComponent getComponent() {
        return label;
    }

    public void showRollResult(int dieOne, int dieTwo) {
        label.setText("Rolled " + dieOne + " + " + dieTwo + " = " + (dieOne + dieTwo));
    }

    public void enableRollButton() {
        // No-op: the board's Roll button remains enabled between turns.
    }

    public void disableRollButton() {
        // No-op.
    }
}
