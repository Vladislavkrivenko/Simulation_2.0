package moveManager;

import coordinates.Coordinates;
import entity.Entity;
import entity.Rock;
import entity.Tree;
import mapManager.EntityManager;

    public class WalkabilityChecker {
        private final EntityManager entityManager;

        public WalkabilityChecker(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public boolean isWalkable(Coordinates coordinates) {
            Entity entity = entityManager.getEntity(coordinates);
            if (entity == null) return true;
            return !(entity instanceof Rock || entity instanceof Tree || entity instanceof Creature);
        }

    }

