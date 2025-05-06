package actionManager;

public class ActionPause implements Action {
    private final SimulationController simulationController;

    public ActionPause(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    @Override
    public void execute() {
        simulationController.pause();
    }
}

