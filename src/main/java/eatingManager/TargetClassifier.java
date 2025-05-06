package eatingManager;

import entity.Grass;
import entity.animal.Herbivore;
import moveManager.SearchAlgorithm;

public class TargetClassifier {
    private final SearchAlgorithm searchAlgorithm;
    private final Creature creature;

    public TargetClassifier(SearchAlgorithm searchAlgorithm, Creature creature) {
        this.searchAlgorithm = searchAlgorithm;
        this.creature = creature;
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
}
