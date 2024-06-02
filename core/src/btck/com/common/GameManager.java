package btck.com.common;

import btck.com.model.constant.GameState;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.view.screens.SlowMo;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameManager {

    public static GameManager gameManager;

    public GameState gameState;

    public Player currentPlayer;

    public Array<Enemy> enemies = new Array<>();

    public static GameManager getInstance(){
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    public void addEnemy(Enemy enemy){
        if(SlowMo.activeAll) SlowMo.activateEntity(enemy);
        this.enemies.add(enemy);
    }

    public int getCurrentEnemyAmount(){
        return enemies.size;
    }
}