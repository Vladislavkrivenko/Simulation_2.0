package moveManager;

import coordinates.Coordinates;
import entity.Entity;
import mapManager.EntityManager;
import mapManager.MapService;

import java.util.*;

public class SearchAlgorithm {
    Creature creature;
    protected Class<? extends Entity> victimClass;

    private EntityManager entityManager;
    private final MapService mapService;
    private final WalkabilityChecker walkabilityChecker;
    private Coordinates targetCoordinates = null;
    private final Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

    public SearchAlgorithm(EntityManager entityManager, MapService mapService, WalkabilityChecker walkabilityChecker) {
        this.entityManager = entityManager;
        this.mapService = mapService;
        this.walkabilityChecker = walkabilityChecker;
    }

    public void setVictim(Class<? extends Entity> victim) {//установить жертву
        this.victimClass = victim;
    }

    public Class<? extends Entity> getVictim() {//возврат жертвы
        return victimClass;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Coordinates> getTargetForFood(MapService mapService, Coordinates start) {
        if (mapService == null) {
            throw new IllegalStateException("entityManager is null in SearchAlgorithm.bfs");
        }

        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();

        cameFrom.clear();
        targetCoordinates = null;

        queue.add(start);
        visited.add(start);


        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            Entity currentEntity = entityManager.getEntity(current);
            if (victimClass != null && victimClass.isInstance(currentEntity)) {
                targetCoordinates = current;
                break;
            }

            for (Coordinates neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        if (targetCoordinates == null) {
            return new ArrayList<>();
        }
        return getPath();
    }

    private List<Coordinates> getPath() {
        List<Coordinates> path = new LinkedList<>();
        if (targetCoordinates == null) return path;

        Coordinates step = targetCoordinates;
        while (step != null && cameFrom.containsKey(step)) {
            path.add(step);
            step = cameFrom.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Coordinates> getNeighbors(Coordinates pos) {
        List<Coordinates> neighbors = new ArrayList<>();
        int x = pos.getMapWidth();
        int y = pos.getMapHeight();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            Coordinates candidate = new Coordinates(nx, ny);

            if (mapService.getInsideMapBorder(candidate)) {
                if (walkabilityChecker.isWalkable(candidate)) {
                    neighbors.add(candidate);
                }
            }
        }

        return neighbors;
    }
}

