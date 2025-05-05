package entity;

import coordinates.Coordinates;

public class Tree extends Entity {
    public static final String SPRITE_OF_TREE = "🌳";

    public Tree(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return SPRITE_OF_TREE;
    }
}