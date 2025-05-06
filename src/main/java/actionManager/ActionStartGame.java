package actionManager;

    public class ActionStartGame implements Action {
        private final SimulationController simulationController;

        public ActionStartGame(SimulationController simulationController) {
            this.simulationController = simulationController;
        }

        @Override
        public void execute() {
            simulationController.runSimulationLoop();
        }
    }
