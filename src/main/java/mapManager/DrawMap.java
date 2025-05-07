package mapManager;

import coordinates.Coordinates;
import entity.Entity;

public class DrawMap {
    private final MapService mapService;
    private final EntityManager entityManager;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String EMPTY_SPRITE = "\033[38;5;94m";//
    public static final String SQUARE_UNICODE = "🏿";

    public DrawMap(MapService mapService, EntityManager entityManager) {
        this.mapService = mapService;
        this.entityManager = entityManager;
    }

    public void drawingMap() {
        for (int colum = 0; colum < mapService.getTotalColumns(); colum++) {
            StringBuilder line = new StringBuilder();
            for (int rows = 0; rows < mapService.getTotalRows(); rows++) {
                Coordinates coordinates = new Coordinates(colum, rows);
                if (mapService.isSquareEmpty(coordinates, entityManager)) {
                    line.append(getSpriteForEmptySquare(coordinates));

                } else {
                    line.append(getEntitySprite(entityManager.getEntity(coordinates)));
                }

            }
            line.append(ANSI_RESET);
            System.out.println(line);
        }
        System.out.println("");
    }

    private String getSpriteForEmptySquare(Coordinates coordinates) {
        return colorizeSprite(EMPTY_SPRITE + SQUARE_UNICODE);
    }

    private String getEntitySprite(Entity entity) {
        if (entity != null) {
            return entity.getSprite();
        }
        return "Unknown image";
    }


    private String colorizeSprite(String sprite) {
        return sprite + ANSI_RESET;
    }
}
