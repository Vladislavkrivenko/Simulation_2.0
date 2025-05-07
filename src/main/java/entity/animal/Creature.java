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
    protected SearchAlgorithm searchAlgorithm;
    protected Class<? extends Entity> victim;

    protected EntityManager entityManager;
    protected final MapService mapService;

    private final CreatureTargetingManager creatureTargetingManager;
    private final AnimalMovement animalMovement;

    public Creature(Coordinates coordinates, String typeOfAnimal, int animalSpeed, Class<? extends Entity> victim, EntityManager entityManager, MapService mapService, SearchAlgorithm searchAlgorithm) {
        super(coordinates);
        this.typeOfAnimal = typeOfAnimal;
        this.animalSpeed = animalSpeed;
        this.victim = victim;
        this.entityManager = entityManager;
        this.mapService = mapService;

        this.searchAlgorithm = new SearchAlgorithm(entityManager, mapService);
        searchAlgorithm.setEntityManager(entityManager);
        this.searchAlgorithm.setCreature(this);
        this.searchAlgorithm.setVictim(victim);

        this.creatureTargetingManager = new CreatureTargetingManager(searchAlgorithm, this, entityManager,mapService);
        this.animalMovement = new AnimalMovement(entityManager, this, mapService);
    }

    public void makeMove() {
        if (victim == null) {
            return;
        }
        searchAlgorithm.setVictim(victim);

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
