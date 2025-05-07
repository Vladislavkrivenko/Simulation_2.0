package eatingManager;

import coordinates.Coordinates;
import entity.Entity;
import mapManager.EntityManager;

public class EatingService {
    private final EntityManager entityManager;
    private final TargetClassifier targetClassifier;

    public EatingService(EntityManager entityManager, TargetClassifier targetClassifier) {
        this.entityManager = entityManager;
        this.targetClassifier = targetClassifier;
    }
    public void eatVictim(Entity predator, Entity target) {
        if (target == null) {
            return;
        }

        if (!targetClassifier.isTargetFood(target)) {
            return;
        }
        Coordinates victimPosition = target.getCoordinates();
        entityManager.removeEntity(victimPosition, target);
    }
}
