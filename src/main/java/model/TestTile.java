package model;

public class TestTile implements Tile{

    private final String name;

    public TestTile(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
