package entity.animal;

import coordinates.Coordinates;
import eatingManager.CreatureTargetingManager;
import entity.Entity;
import mapManager.EntityManager;
import mapManager.MapService;
import moveManager.AnimalMovement;
import moveManager.SearchAlgorithm;

public abstract class Creature extends Entity {
    protected String typeOfAnimal;
    protected int animalSpeed;
    private final CreatureTargetingManager creatureTargetingManager;
    private final AnimalMovement animalMovement;

    public Creature(Coordinates coordinates, String typeOfAnimal, int animalSpeed, MapService mapService) {
        super(coordinates);
        this.typeOfAnimal = typeOfAnimal;
        this.animalSpeed = animalSpeed;
        EntityManager entityManager = mapService.getEntityManager();
        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(entityManager, mapService);
        searchAlgorithm.setCreature(this);
        searchAlgorithm.setVictim(getVictimType());
        this.creatureTargetingManager = new CreatureTargetingManager(searchAlgorithm, this, entityManager, mapService);
        this.animalMovement = new AnimalMovement(entityManager, this, mapService);
    }

    public abstract Class<? extends Entity> getVictimType();

    public void makeMove() {
        if (creatureTargetingManager.checkAdjacentAndEat()) {
            return;
        }
        if (creatureTargetingManager.pursueAndMaybeEat()) {
            return;
        }
        animalMovement.moveRandomly();
    }

    public int getAnimalSpeed() {
        return animalSpeed;
    }

    public final void setPosition(Coordinates newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("New position cannot be null");
        }
        super.setCoordinates(newPosition);
    }

}
