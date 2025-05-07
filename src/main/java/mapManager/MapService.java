package mapManager;

import coordinates.Coordinates;
import entity.Entity;
import entity.Rock;
import entity.Tree;
import entity.animal.Creature;

public class MapService {
    private final int totalRows;
    private final int totalColumns;
    private final EntityManager entityManager;

    public MapService(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.entityManager = new EntityManager();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public boolean isInsideMapBorder(Coordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getX() < totalRows
                && coordinates.getY() >= 0 && coordinates.getY() < totalColumns;
    }

    public boolean isSquareEmpty(Coordinates coordinates, EntityManager entityManager) {
        return !entityManager.getLocationOfEntity().containsKey(coordinates);
    }

    public boolean isWalkable(Coordinates coordinates) {
        Entity entity = entityManager.getEntity(coordinates);
        if (entity == null) return true;
        return !(entity instanceof Rock || entity instanceof Tree || entity instanceof Creature);
    }

}
