package gameManager;

import mapManager.EntityManager;

public class Main {
    public static void main(String[] args) {
        GameMenu gameMenu = new GameMenu(new EntityManager());
        gameMenu.startGame();
    }
}
