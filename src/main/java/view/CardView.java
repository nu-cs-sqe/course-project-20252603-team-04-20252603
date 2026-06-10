package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Card;

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

public class CardView {

    private static final Color INK = new Color(0x16, 0x1D, 0x19);
    private static final Color PRIMARY = new Color(0x00, 0x6C, 0x49);

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
            dialog = new JDialog(owner, "Chance", false);
            dialog.setSize(380, 220);
        }
        dialog.setLocationRelativeTo(owner);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setBorder(new EmptyBorder(20, 24, 16, 24));

        JLabel title = new JLabel(card.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY);

        JLabel description = new JLabel(
                "<html><div style='text-align:center'>" + card.getDescription() + "</div></html>",
                SwingConstants.CENTER);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(INK);

        JButton proceed = new JButton("Proceed");
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
