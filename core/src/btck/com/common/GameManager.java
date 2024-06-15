package btck.com.common;

import btck.com.model.state.GameState;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.crowd_control.SlowMo;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameManager {
    private boolean btnMusicActive = false;
    private boolean btnSfxActive = false;
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
    public boolean isMusicActive() {
        return btnMusicActive;
    }
    public void setMusicActive(boolean musicActive) {
        this.btnMusicActive = musicActive;
    }

    public boolean isSfxActive() {
        return btnSfxActive;
    }
    public void setSfxActive(boolean sfxActive) {
        this.btnSfxActive = sfxActive;
    }
    public void resetSoundSettings() {
        this.btnMusicActive = false;
        this.btnSfxActive = false;
    }
}