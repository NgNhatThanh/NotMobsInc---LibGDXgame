package btck.com.model.entity.player;

import btck.com.common.GameManager;
import com.badlogic.gdx.Gdx;

public class Blinking {
    static public boolean blinking = false;
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
            GameManager.getInstance().getCurrentPlayer().setVisible((int) (curTime / invisibleTime) % 2 != 0);
        }
        else{
            blinking = false;
            GameManager.getInstance().getCurrentPlayer().setVisible(true);
            GameManager.getInstance().getCurrentPlayer().getAttack().setCurrentDamage(GameManager.getInstance().getCurrentPlayer().getAttack().getDamage());
            GameManager.getInstance().getCurrentPlayer().setVulnerable(true);
        }
    }

}
