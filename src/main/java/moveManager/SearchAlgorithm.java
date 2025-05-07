package moveManager;

import coordinates.Coordinates;
import entity.Entity;
import entity.animal.Creature;
import mapManager.EntityManager;
import mapManager.MapService;

import java.util.*;

public class SearchAlgorithm {
    Creature creature;
    private Class<? extends Entity> victimClass;

    private EntityManager entityManager;
    private final MapService mapService;

    private Coordinates targetCoordinates = null;
    private final Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

    public SearchAlgorithm(EntityManager entityManager, MapService mapService) {
        this.entityManager = entityManager;
        this.mapService = mapService;
    }

    public void setVictim(Class<? extends Entity> victim) {
        this.victimClass = victim;
    }

    public Class<? extends Entity> getVictim() {
        return victimClass;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Coordinates> findPathToNearestVictim(Coordinates start) {

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

            for (Coordinates neighbor : getWalkableNeighbors(current)) {
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

    public List<Coordinates> getWalkableNeighbors(Coordinates pos) {
        List<Coordinates> neighbors = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            Coordinates candidate = new Coordinates(nx, ny);

            if (mapService.isInsideMapBorder(candidate) && mapService.isWalkable(candidate)) {
                neighbors.add(candidate);
            }
        }

        return neighbors;
    }
}