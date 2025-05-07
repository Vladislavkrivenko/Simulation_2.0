package gameManager;

import entity.animal.Creature;
import entity.animal.Herbivore;
import mapManager.DrawMap;
import mapManager.EntityManager;

import java.util.List;

public class Simulation {
    private final Object pauseLock = new Object();
    private boolean paused = false;
    private boolean running = true;
    private List<Creature> animals;
    private DrawMap drawMap;
    private EntityManager entityManager;

    public void init(List<Creature> animals, DrawMap drawMap, EntityManager entityManager) {
        this.animals = animals;
        this.drawMap = drawMap;
        this.entityManager = entityManager;
    }

    public void pause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void stop() {
        synchronized (pauseLock) {
            running = false;
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void runSimulationLoop() {
        while (running) {
            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            if (noHerbivoresLeft()) {
                System.out.println("There are no hares left on the map! The game ends.");
                stop();
                break;
            }

            for (Creature creature : animals) {
                creature.makeMove();
            }
            drawMap.drawingMap();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void oneIteration() {
        synchronized (pauseLock) {
            for (Creature creature : animals) {
                creature.makeMove();
            }
            drawMap.drawingMap();
        }
    }

    private boolean noHerbivoresLeft() {
        return entityManager.getLocationOfEntity().values().stream().noneMatch(e -> e instanceof Herbivore);
    }
}
