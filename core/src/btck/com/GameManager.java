package btck.com;

import btck.com.model.entity.Player;

public class GameManager {

    public static GameManager gameManager;

    Player currentPlayer;

    public static GameManager getInstance(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }
}
