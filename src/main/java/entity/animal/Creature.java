package entity.animal;

import coordinates.Coordinates;
import eatingManager.EatingService;
import eatingManager.TargetClassifier;
import entity.Entity;
import mapManager.EntityManager;
import mapManager.MapService;
import moveManager.MovementService;
import moveManager.SearchAlgorithm;
import moveManager.WalkabilityChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature extends Entity {
    protected String typeOfAnimal;
    protected int animalSpeed;
    protected SearchAlgorithm searchAlgorithm;
    protected Class<? extends Entity> victim;

    protected EntityManager entityManager;
    protected final MapService mapService;

    private final EatingService eatingService;
    private final TargetClassifier targetClassifier;
    private final MovementService movementService;

    public Creature(Coordinates coordinates, String typeOfAnimal, int animalSpeed, Class<? extends Entity> victim, EntityManager entityManager, MapService mapService,SearchAlgorithm searchAlgorithm) {
        super(coordinates);
        this.typeOfAnimal = typeOfAnimal;
        this.animalSpeed = animalSpeed;
        this.victim = victim;
        this.entityManager = entityManager;
        this.mapService = mapService;

        this.searchAlgorithm = new SearchAlgorithm(entityManager,mapService,new WalkabilityChecker(entityManager));
        searchAlgorithm.setEntityManager(entityManager);
        this.searchAlgorithm.setCreature(this);
        this.searchAlgorithm.setVictim(victim);

        this.targetClassifier = new TargetClassifier(searchAlgorithm, this);
        this.eatingService = new EatingService(entityManager, targetClassifier);
        this.movementService = new MovementService(entityManager);


    }
    public void makeMove() {
        if (victim == null) {
            return;
        }

        searchAlgorithm.setVictim(victim);

        if (checkAdjacentAndEat()) {
            return;
        }

        if (pursueAndMaybeEat()) {
            return;
        }
        moveRandomly();
    }

    private boolean checkAdjacentAndEat() {
        Coordinates currentPos = getCoordinates();

        for (Coordinates neighbor : searchAlgorithm.getNeighbors(currentPos)) {
            Entity entity = entityManager.getEntity(neighbor);

            if (targetClassifier.isTargetFood(entity)) {
                eatingService.eatVictim(this, entity);
                movementService.moveTo(this, neighbor);
                return true;
            }
        }

        return false;
    }

    private boolean pursueAndMaybeEat() {
        Coordinates currentPos = getCoordinates();
        List<Coordinates> path = searchAlgorithm.getTargetForFood(mapService, currentPos);

        if (path == null || path.isEmpty()) {
            return false;
        }

        if (path.size() == 2) {
            Coordinates victimCoordinates = path.get(1);
            Entity entityAtTarget = entityManager.getEntity(victimCoordinates);

            if (targetClassifier.isTargetFood(entityAtTarget)) {
                eatingService.eatVictim(this, entityAtTarget);
                movementService.moveTo(this, victimCoordinates);
                return true;
            }
        }

        Coordinates nextStep = path.get(Math.min(getAnimalSpeed(), path.size() - 1));
        movementService.moveTo(this, nextStep);
        return true;
    }


    public void moveRandomly() {
        Random random = new Random();
        List<Coordinates> possibleMoves = new ArrayList<>();

        for (int dx = -getAnimalSpeed(); dx <= getAnimalSpeed(); dx++) {
            for (int dy = -getAnimalSpeed(); dy <= getAnimalSpeed(); dy++) {
                if (Math.abs(dx) + Math.abs(dy) > getAnimalSpeed() || (dx == 0 && dy == 0)) continue;

                int newX = getCoordinates().getMapWidth() + dx;
                int newY = getCoordinates().getMapHeight() + dy;

                Coordinates newCoordinates = new Coordinates(newX, newY);

                if (mapService.getInsideMapBorder(newCoordinates) && mapService.getSquareEmpty(newCoordinates, entityManager)) {
                    possibleMoves.add(newCoordinates);
                }
            }
        }

        if (!possibleMoves.isEmpty()) {
            Coordinates randomPosition = possibleMoves.get(random.nextInt(possibleMoves.size()));
            movementService.moveTo(this, randomPosition);
        }
    }

    public int getAnimalSpeed() {
        return animalSpeed;
    }

    public void setPosition(Coordinates newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("New position cannot be null");
        }
        super.setCoordinates(newPosition);

    }

}
