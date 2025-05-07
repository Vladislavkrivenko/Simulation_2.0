package eatingManager;

import coordinates.Coordinates;
import entity.Entity;
import entity.Grass;
import entity.animal.Creature;
import entity.animal.Herbivore;
import entity.animal.Predator;
import mapManager.EntityManager;
import mapManager.MapService;
import moveManager.AnimalMovement;
import moveManager.SearchAlgorithm;

import java.util.List;

public class CreatureTargetingManager {
    private final SearchAlgorithm searchAlgorithm;
    private final Creature creature;
    private final EntityManager entityManager;
    private final AnimalMovement animalMovement;
    private final MapService mapService;

    public CreatureTargetingManager(SearchAlgorithm searchAlgorithm, Creature creature, EntityManager entityManager, MapService mapService) {
        this.searchAlgorithm = searchAlgorithm;
        this.creature = creature;
        this.entityManager = entityManager;
        this.mapService = mapService;
        this.animalMovement = new AnimalMovement(entityManager, creature, mapService);
    }

    public boolean isTargetFood(Entity entity) {
        Class<? extends Entity> victim = searchAlgorithm.getVictim();
        if (victim != null) {
            return victim.isInstance(entity);
        }

        if (creature instanceof Herbivore) {
            return entity instanceof Grass;
        }
        if (creature instanceof Predator) {
            return entity instanceof Herbivore;
        }
        return false;
    }

    public void eatVictim(Entity predator, Entity target) {
        if (target == null) {
            return;
        }

        if (!isTargetFood(target)) {
            return;
        }
        Coordinates victimPosition = target.getCoordinates();
        entityManager.removeEntity(victimPosition, target);
    }

    public AnimalMovement getAnimalMovement() {
        return animalMovement;
    }

    public boolean checkAdjacentAndEat() {
        Coordinates currentPos = creature.getCoordinates();

        for (Coordinates neighbor : searchAlgorithm.getWalkableNeighbors(currentPos)) {
            Entity entity = entityManager.getEntity(neighbor);

            if (isTargetFood(entity)) {
                eatVictim(creature, entity);
                animalMovement.moveTo(creature, neighbor);
                return true;
            }
        }

        return false;
    }

    public boolean pursueAndMaybeEat() {
        Coordinates currentPos = creature.getCoordinates();
        List<Coordinates> path = searchAlgorithm.findPathToNearestVictim(currentPos);

        if (path == null || path.isEmpty()) {
            return false;
        }

        if (path.size() == 2) {
            Coordinates victimCoordinates = path.get(1);
            Entity entityAtTarget = entityManager.getEntity(victimCoordinates);

            if (isTargetFood(entityAtTarget)) {
                eatVictim(creature, entityAtTarget);
                animalMovement.moveTo(creature, victimCoordinates);
                return true;
            }
        }

        Coordinates nextStep = path.get(Math.min(creature.getAnimalSpeed(), path.size() - 1));
        animalMovement.moveTo(creature, nextStep);
        return true;
    }

    public MapService getMapService() {
        return mapService;
    }
}
