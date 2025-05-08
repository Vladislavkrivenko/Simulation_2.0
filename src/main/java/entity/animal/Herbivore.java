package entity.animal;

import coordinates.Coordinates;
import entity.Entity;
import entity.Grass;
import mapManager.MapService;

public class Herbivore extends Creature {
    public static final String SPRITE_OF_HERBIVORE = "🐰";
    public Herbivore(Coordinates coordinates, MapService mapService) {
        super(coordinates, "Rabbit", 2,mapService);
    }

    @Override
    public Class<? extends Entity> getVictimType() {
        return Grass.class;
    }

    @Override
    public String getSprite() {
        return SPRITE_OF_HERBIVORE;
    }

}

