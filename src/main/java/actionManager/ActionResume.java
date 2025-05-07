package actionManager;

import gameManager.Simulation;

public class ActionResume implements Action {
    private final Simulation simulation;

    public ActionResume(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute() {
        simulation.resume();
    }
}
