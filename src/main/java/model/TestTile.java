package model;

public class TestTile implements Tile{

    private final String name;

    public TestTile(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Tile name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
