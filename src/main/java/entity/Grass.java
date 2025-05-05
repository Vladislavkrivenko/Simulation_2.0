package entity;

import coordinates.Coordinates;

public class Grass extends Entity {
    public static final String SPRITE_OF_GRASS = "🍀";

    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return SPRITE_OF_GRASS;
    }
}
