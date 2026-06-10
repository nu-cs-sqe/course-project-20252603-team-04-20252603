package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Player;
import model.Property;
import util.LocalizationManager;

import javax.swing.JOptionPane;
import java.awt.Frame;
import java.util.Locale;
import java.util.Objects;

public class RentConfirmationView {

    private final Frame owner;

    public RentConfirmationView() {
        this(null);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The Swing parent frame is shared by reference solely for dialog ownership.")
    public RentConfirmationView(Frame owner) {
        this.owner = owner;
    }

    public void showRentPaid(Player renter, Property property) {
        Objects.requireNonNull(renter, "Renter cannot be null");
        Objects.requireNonNull(property, "Property cannot be null");

        JOptionPane.showMessageDialog(
                owner,
                LocalizationManager.formatMessage(
                        "rentConfirmation.message",
                        renter.getName(),
                        formatMoney(property.getRent()),
                        property.getPropertyName()),
                LocalizationManager.getMessage("rentConfirmation.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static String formatMoney(double amount) {
        return String.format(Locale.US, "$%,d", (int) amount);
    }
}
