package entity.animal;

import coordinates.Coordinates;
import entity.Entity;
import mapManager.MapService;


public class Predator extends Creature {
    public static final String SPRITE_OF_PREDATOR = "🐺";
        public Predator(Coordinates coordinates, MapService mapService) {
            super(coordinates, "Wolf", 2,mapService);

        }

        @Override
        public String getSprite() {
            return SPRITE_OF_PREDATOR;
        }

    @Override
    public Class<? extends Entity> getVictimType() {
        return Herbivore.class;
    }
}

