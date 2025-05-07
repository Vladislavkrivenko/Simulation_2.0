package actionManager;

import gameManager.Simulation;

public class ActionPause implements Action {
    private final Simulation simulation;

    public ActionPause(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute() {
        simulation.pause();
    }
}

