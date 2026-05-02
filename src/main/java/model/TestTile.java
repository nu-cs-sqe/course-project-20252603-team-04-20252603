package model;

public class TestTile implements Tile{

    private final TileType name;

    public TestTile(TileType name) {
        if (name == null) {
            throw new NullPointerException("Tile name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public TileType getName() {
        return name;
    }
}
