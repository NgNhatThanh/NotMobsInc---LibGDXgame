package btck.com;

import btck.com.model.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager {

    public static GameManager gameManager;

    Player currentPlayer;

    public static GameManager getInstance(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }
}
