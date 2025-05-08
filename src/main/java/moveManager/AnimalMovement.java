package moveManager;

import coordinates.Coordinates;
import entity.animal.Creature;
import mapManager.EntityManager;
import mapManager.MapService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalMovement {
    private final EntityManager entityManager;
    private final Creature creature;
    private final MapService mapService;

    public AnimalMovement(EntityManager entityManager, Creature creature, MapService mapService) {
        this.entityManager = entityManager;
        this.creature = creature;
        this.mapService = mapService;

    }

    public void moveTo(Creature creature, Coordinates newPosition) {
        Coordinates oldPosition = creature.getCoordinates();
        entityManager.removeEntity(oldPosition, creature);

        creature.setPosition(newPosition);
        entityManager.setEntity(newPosition, creature);
    }

    public void moveRandomly() {
        Random random = new Random();
        List<Coordinates> possibleMoves = new ArrayList<>();

        for (int dx = -creature.getAnimalSpeed(); dx <= creature.getAnimalSpeed(); dx++) {
            for (int dy = -creature.getAnimalSpeed(); dy <= creature.getAnimalSpeed(); dy++) {
                if (Math.abs(dx) + Math.abs(dy) > creature.getAnimalSpeed() || (dx == 0 && dy == 0)) continue;

                int newX = creature.getCoordinates().getX() + dx;
                int newY = creature.getCoordinates().getY() + dy;

                Coordinates newCoordinates = new Coordinates(newX, newY);

                if (mapService.isInsideMapBorder(newCoordinates) && mapService.isSquareEmpty(newCoordinates)) {
                    possibleMoves.add(newCoordinates);
                }
            }
        }

        if (!possibleMoves.isEmpty()) {
            Coordinates randomPosition = possibleMoves.get(random.nextInt(possibleMoves.size()));
            moveTo(creature, randomPosition);
        }
    }
}

