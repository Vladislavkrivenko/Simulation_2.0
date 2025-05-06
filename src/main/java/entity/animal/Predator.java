package entity.animal;

import coordinates.Coordinates;
import mapManager.EntityManager;
import mapManager.MapService;

public class Predator  {
    public class Predator extends Creature {
        public Predator(Coordinates coordinates, String typeOfAnimal, int animalSpeed, EntityManager entityManager, MapService mapService, SearchAlgorithm searchAlgorithm) {
            super(coordinates, "Wolf", 2, Herbivore.class, entityManager, mapService,searchAlgorithm);

        }

        @Override
        public void setPosition(Coordinates newPosition) {
            super.setPosition(newPosition);
        }

        @Override
        public String getSprite() {
            return "🐺";
        }
    }

}
