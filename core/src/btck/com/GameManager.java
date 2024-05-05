package btck.com;

import btck.com.model.constant.GameState;
import btck.com.model.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameManager {

    public static GameManager gameManager;

    public GameState gameState;

    Player currentPlayer;

    public static GameManager getInstance(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

}