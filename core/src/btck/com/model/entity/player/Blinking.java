package btck.com.model.entity.player;

import btck.com.GameManager;
import com.badlogic.gdx.Gdx;
import lombok.Getter;

public class Blinking {
    static public boolean blinking = false;
    static public boolean appearing = true;
    static private float curTime = 0;
    static private float blinkDuration = 0.8f;
    static private float invisibleTime = 0.08f;

    public static void blink(){
        blinking = true;
        GameManager.getInstance().getCurrentPlayer().getAttack().setCurrentDamage(0);
        GameManager.getInstance().getCurrentPlayer().setVulnerable(false);
        curTime = 0;
    }

    public static void update(){
        curTime += Gdx.graphics.getDeltaTime();
        if(curTime <= blinkDuration){
            appearing = (int) (curTime / invisibleTime) % 2 != 0;
        }
        else{
            blinking = false;
            appearing = true;
            GameManager.getInstance().getCurrentPlayer().getAttack().setCurrentDamage(GameManager.getInstance().getCurrentPlayer().getAttack().getDamage());
            GameManager.getInstance().getCurrentPlayer().setVulnerable(true);
        }
    }

}
