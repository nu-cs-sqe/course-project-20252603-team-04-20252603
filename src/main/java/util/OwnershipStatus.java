package util;

public enum OwnershipStatus {
    UNOWNED("UNOWNED"),
    OWNED("OWNED");

    private final String displayName;
    
    OwnershipStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}