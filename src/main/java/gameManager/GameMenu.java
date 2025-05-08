package gameManager;

import actionManager.*;
import coordinates.Coordinates;
import entity.Entity;
import entity.animal.Creature;
import mapManager.CreateEntityOnMap;
import mapManager.DrawMap;
import mapManager.EntityManager;
import mapManager.MapService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMenu {
    private static final int START_GAME = 1;
    private static final int PAUSE = 2;
    private static final int RESUME = 3;
    private static final int ITERATE = 4;
    private static final int STOP_GAME = 5;

    private final Simulation simulation = new Simulation();
    private Actions actions;
    private final UserInput userInput = new UserInput();
    private final EntityManager entityManager;
    private List<Creature> animal;
    private DrawMap drawMap;

    private boolean gameStarted = false;

    public GameMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void startGame() {
        while (true) {
            startGameLoop();
        }
    }

    private void startGameLoop() {
        int choice = userInput.gameMenu();
        switch (choice) {
            case START_GAME:
                if (!gameStarted) {
                    game();
                } else {
                    System.out.println("The game is already running.");
                }
                break;
            case PAUSE:
                if (gameStarted) {
                    actions.pauseGame();
                    System.out.println("The game is paused!");
                } else {
                    System.out.println("First, launch the game.");
                }
                break;
            case RESUME:
                if (gameStarted) {
                    actions.resumeGame();
                    System.out.println("Game resume!");
                } else {
                    System.out.println("First, launch the game.");
                }
                break;
            case ITERATE:
                if (gameStarted) {
                    System.out.println("One simulation step:");
                    if (animal != null && drawMap != null) {
                        actions.oneIteration();
                    }
                } else {
                    System.out.println("First, launch the game.");
                }
                break;
            case STOP_GAME:
                if (gameStarted) {
                    actions.stopGame();
                    System.out.println("Game stopped.");
                    gameStarted = false;
                } else {
                    System.out.println("The simulation has not yet started.");
                }
                break;
            default:
                System.out.println("Unknown command.");

        }
    }

    private void game() {
        System.out.println("The game has begun!");

        int width = userInput.inputNumbersFromUser("Enter the map width:");
        int height = userInput.inputNumbersFromUser("Enter the height of the map:");

        CreateEntityOnMap game = new CreateEntityOnMap(width, height, entityManager);
        game.fillTheMapWithEntity();

        MapService mapService = game.getMapService();
        EntityManager entityManager = game.getEntityManager();

        drawMap = new DrawMap(mapService, entityManager);
        animal = new ArrayList<>();

        for (Map.Entry<Coordinates, Entity> entry : game.getEntityManager().getLocationOfEntity().entrySet()) {
            if (entry.getValue() instanceof Creature creature) {
                animal.add(creature);
            }
        }
        drawMap.drawingMap();

        simulation.init(animal, drawMap, entityManager);

        Action start = new ActionStartGame(simulation);
        Action pause = new ActionPause(simulation);
        Action resume = new ActionResume(simulation);
        Action iteration = new ActionsOneIteration(simulation);
        Action stop = new ActionStop(simulation);

        actions = new Actions(start, pause, resume, iteration, stop);

        Thread simulationThread = new Thread(() -> actions.startGame());
        simulationThread.start();
        gameStarted = true;
    }
}
