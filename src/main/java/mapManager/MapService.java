package mapManager;

import coordinates.Coordinates;

public class MapService {
    private final int totalRows;
    private final int totalColumns;

    public MapService(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;

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

}
