package view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.Player;
import model.Property;
import util.LocalizationManager;

import javax.swing.JOptionPane;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BankruptcyView {

    private final Frame owner;
    private Property selectedProperty;

    public BankruptcyView() {
        this(null);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The Swing parent frame is shared by reference solely for dialog ownership.")
    public BankruptcyView(Frame owner) {
        this.owner = owner;
    }

    public void showForcedSaleOptions(Player player) {
        Objects.requireNonNull(player, "Player cannot be null");
        selectedProperty = null;

        List<Property> properties = new ArrayList<>(player.getOwnedProperties());
        properties.sort(Comparator.comparing(Property::getPropertyName));

        if (properties.isEmpty()) {
            JOptionPane.showMessageDialog(
                    owner,
                    LocalizationManager.formatMessage("bankruptcy.noProperties", player.getName()),
                    LocalizationManager.getMessage("bankruptcy.title"),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        PropertyChoice[] choices = createChoices(properties);
        Object selected = JOptionPane.showInputDialog(
                owner,
                LocalizationManager.formatMessage("bankruptcy.forcedSalePrompt", player.getName()),
                LocalizationManager.getMessage("bankruptcy.forcedSaleTitle"),
                JOptionPane.WARNING_MESSAGE,
                null,
                choices,
                choices[0]);

        if (selected instanceof PropertyChoice) {
            selectedProperty = ((PropertyChoice) selected).property;
        }
    }

    public void showPlayerEliminated(Player player) {
        Objects.requireNonNull(player, "Player cannot be null");
        JOptionPane.showMessageDialog(
                owner,
                LocalizationManager.formatMessage("bankruptcy.eliminated", player.getName()),
                LocalizationManager.getMessage("bankruptcy.title"),
                JOptionPane.WARNING_MESSAGE);
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Returns the model property selected by the player for controller processing.")
    public Property getSelectedProperty() {
        return selectedProperty;
    }

    private static PropertyChoice[] createChoices(List<Property> properties) {
        PropertyChoice[] choices = new PropertyChoice[properties.size()];
        for (int i = 0; i < properties.size(); i++) {
            choices[i] = new PropertyChoice(properties.get(i));
        }
        return choices;
    }

    private static String formatMoney(double amount) {
        return String.format(Locale.US, "$%,d", (int) amount);
    }

    private static final class PropertyChoice {
        private final Property property;

        private PropertyChoice(Property property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return LocalizationManager.formatMessage(
                    "bankruptcy.saleValue",
                    property.getPropertyName(),
                    formatMoney(property.getResaleValue()));
        }
    }
}
