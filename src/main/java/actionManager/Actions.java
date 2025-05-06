package actionManager;

public class Actions {
    Action start;
    Action pause;
    Action resume;
    Action iteration;
    Action stop;

    public Actions(Action start, Action pause, Action resume, Action iteration, Action stop) {
        this.start = start;
        this.pause = pause;
        this.resume = resume;
        this.iteration = iteration;
        this.stop = stop;
    }

    public void startGame() {
        start.execute();
    }

    public void pauseGame() {
        pause.execute();
    }

    public void resumeGame() {
        resume.execute();
    }

    public void oneIteration() {
        iteration.execute();
    }

    public void stopGame() {
        stop.execute();
    }

}
