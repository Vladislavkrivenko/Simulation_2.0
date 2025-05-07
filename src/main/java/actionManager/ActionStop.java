package actionManager;

import gameManager.Simulation;

public class ActionStop implements Action {
    private final Simulation simulation;

    public ActionStop(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute() {
        simulation.stop();
    }
}
