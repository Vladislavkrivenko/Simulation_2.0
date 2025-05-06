package entity.animal;

import coordinates.Coordinates;
import entity.Grass;
import mapManager.EntityManager;
import mapManager.MapService;

public class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates, String typeOfAnimal, int animalSpeed, EntityManager entityManager, MapService mapService, SearchAlgorithm searchAlgorithm) {
        super(coordinates, "Rabbit", 2, Grass.class, entityManager, mapService,searchAlgorithm);
    }

    @Override
    public void setPosition(Coordinates newPosition) {
        super.setPosition(newPosition);
    }

    @Override
    public String getSprite() {
        return "🐰";
    }

}

