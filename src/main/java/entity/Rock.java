package entity;

import coordinates.Coordinates;

public class Rock extends Entity {
    public static final String SPRITE_OF_ROCK = "🗻";

    public Rock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return SPRITE_OF_ROCK;
    }
}
