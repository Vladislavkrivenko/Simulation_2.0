package actionManager;

import gameManager.Simulation;

public class ActionsOneIteration implements Action{
    private final Simulation simulation;

    public ActionsOneIteration(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute() {
        simulation.oneIteration();
    }
}
