package btck.com.utils;

import btck.com.GameManager;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import lombok.Setter;

public class Debugger {

    static ShapeRenderer shapeRenderer;

    public static Debugger debugger;

    public static Debugger getInstance(){
        if(debugger == null) debugger = new Debugger();
        return debugger;
    }

    public Debugger(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    @Setter
    public static DEBUG_MODE debugMode = DEBUG_MODE.ON;

    public static void debug(){
        Rectangle playerHitbox = GameManager.getInstance().getCurrentPlayer().getHitbox();

        shapeRenderer.begin();
        shapeRenderer.rect(playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height);

        for(Enemy enemy : GameManager.getInstance().enemies){
            Rectangle enemyHitbox = enemy.getHitbox();
            shapeRenderer.rect(enemyHitbox.x, enemyHitbox.y, enemyHitbox.width, enemyHitbox.height);
        }

        shapeRenderer.end();
    }
}
