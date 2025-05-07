package actionManager;

import gameManager.Simulation;

public class ActionStartGame implements Action {
    private final Simulation simulation;

    public ActionStartGame(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute() {
        simulation.runSimulationLoop();
    }
}
