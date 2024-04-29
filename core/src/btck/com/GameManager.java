package btck.com;

import btck.com.model.constant.GameState;
import btck.com.model.entity.Player;

public class GameManager {

    public static GameManager gameManager;

    public GameState gameState;

    Player currentPlayer;

    public static GameManager getInstance(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
