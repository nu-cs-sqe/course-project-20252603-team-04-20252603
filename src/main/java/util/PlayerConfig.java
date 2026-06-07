package util;

import javax.swing.ImageIcon;
import java.util.Objects;

public class PlayerConfig {

    private final String name;
    private final ImageIcon icon;

    public PlayerConfig(String name, ImageIcon icon) {
        this.name = Objects.requireNonNull(name, "Player name cannot be null");
        this.icon = new ImageIcon(
                Objects.requireNonNull(icon, "Player icon cannot be null").getImage()
        );
    }

}