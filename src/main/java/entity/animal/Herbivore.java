package entity.animal;

import coordinates.Coordinates;
import entity.Grass;
import mapManager.EntityManager;
import mapManager.MapService;
import moveManager.SearchAlgorithm;

public class Herbivore extends Creature {
    public static final String SPRITE_OF_HERBIVORE = "🐰";
    public Herbivore(Coordinates coordinates, String typeOfAnimal, int animalSpeed, EntityManager entityManager, MapService mapService, SearchAlgorithm searchAlgorithm) {
        super(coordinates, "Rabbit", 2, Grass.class, entityManager, mapService,searchAlgorithm);
    }

    @Override
    public String getSprite() {
        return SPRITE_OF_HERBIVORE;
    }

}

