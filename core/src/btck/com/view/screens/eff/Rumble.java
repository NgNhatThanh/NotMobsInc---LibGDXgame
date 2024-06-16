package btck.com.view.screens.eff;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

import java.util.Random;

public class Rumble {

    private static float rumbleDuration = .2f;
    private static float currentTime;
    private static float power = 5f;
    private static float currentPower;
    private static Random random = new Random();
    private static Vector2 pos = new Vector2(0, 0);
    static int cnt = 0;
    @Getter
    private static boolean rumbling = false;

    public static void rumble(){
        currentTime = 0;
        rumbling = true;
    }

    public static Vector2 tick(float delta){
        if(currentTime <= rumbleDuration){
            ++cnt;
            currentPower = power * ((rumbleDuration - currentTime) / rumbleDuration);

            pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;

            currentTime += delta;
        }
        else{
            rumbling = false;
        }
        return pos;
    }

}
