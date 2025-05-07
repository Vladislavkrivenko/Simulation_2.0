package mapManager;

import coordinates.Coordinates;
import entity.Entity;
import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.animal.Herbivore;
import entity.animal.Predator;
import moveManager.SearchAlgorithm;
import moveManager.WalkabilityChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CreateEntityOnMap {
    private static final int TOTAL_PERCENT_ENTITY = 5;
    private final EntityManager entityManager;
    private final MapService mapService;
    private final WalkabilityChecker  walkabilityChecker;
    private final SearchAlgorithm searchAlgorithm;

    public CreateEntityOnMap(int rows, int columns) {
        this.mapService = new MapService(rows, columns);
        this.entityManager = new EntityManager();
        this.walkabilityChecker = new WalkabilityChecker(entityManager);
        this.searchAlgorithm =new SearchAlgorithm(entityManager,mapService,walkabilityChecker);
    }

    public void fillTheMapWithEntity() {

        int totalCells = mapService.getTotalRows() * mapService.getTotalColumns();

        int countingSpawnObjectsOnTheMap = Math.max(1, (int) Math.ceil((TOTAL_PERCENT_ENTITY / 100.0) * totalCells));
        Set<Coordinates> occupiedCells = entityManager.getOccupiedCells();

        for (EnumEntity enumEntity : EnumEntity.values()) {
            int count = 0;
            while (count < countingSpawnObjectsOnTheMap) {
                Coordinates randomCell = randomCell();

                if (randomCell == null || occupiedCells.contains(randomCell)) {
                    continue;
                }

                entityManager.setEntity(randomCell, createEntity(enumEntity, randomCell));
                count++;
            }
        }
    }

    private Entity createEntity(EnumEntity enumEntity, Coordinates coordinates) {
        switch (enumEntity) {
            case ROCK:
                return new Rock(coordinates);
            case GRASS:
                return new Grass(coordinates);
            case TREE:
                return new Tree(coordinates);
            case HERBIVORE:
                return new Herbivore(coordinates, "Rabbit", 2, entityManager, mapService,searchAlgorithm);
            case PREDATOR:
                return new Predator(coordinates, "Wolf", 2, entityManager, mapService,searchAlgorithm);
            default:
                throw new IllegalArgumentException("Unknown entity type" + enumEntity);

        }

    }

    private Coordinates randomCell() {

        Random random = new Random();

        List<Coordinates> emptyCell = new ArrayList<>();

        for (int i = 0; i < mapService.getTotalRows(); i++) {
            for (int j = 0; j < mapService.getTotalColumns(); j++) {
                Coordinates coordinates = new Coordinates(i, j);

                if (!entityManager.getOccupiedCells().contains(coordinates)) {
                    emptyCell.add(coordinates);
                }
            }
        }

        if (emptyCell.isEmpty()) {
            return null;
        }

        return emptyCell.get(random.nextInt(emptyCell.size()));

    }

    public MapService getMapService() {
        return mapService;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
