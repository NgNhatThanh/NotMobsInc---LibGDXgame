package btck.com.utils;

import btck.com.GameManager;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import lombok.Setter;

public class Debugger {

    static ShapeRenderer shapeRenderer;

    public static Debugger debugger;

    @Setter
    public static DEBUG_MODE debugMode = DEBUG_MODE.OFF;

    public static Debugger getInstance(){
        if(debugger == null) debugger = new Debugger();
        return debugger;
    }

    public Debugger(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    public static void debug(){
        Rectangle playerHitbox = GameManager.getInstance().getCurrentPlayer().getHitbox();
        Rectangle playerAttackHB = GameManager.getInstance().getCurrentPlayer().getAttack().getHitbox();

        shapeRenderer.begin();
        shapeRenderer.rect(playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height);
        shapeRenderer.rect(playerAttackHB.x, playerAttackHB.y, playerAttackHB.width, playerAttackHB.height);

        for(Enemy enemy : GameManager.getInstance().enemies){
            Rectangle enemyHitbox = enemy.getHitbox();
            Rectangle enemyAttackHB = enemy.getAttack().getHitbox();
            shapeRenderer.rect(enemyHitbox.x, enemyHitbox.y, enemyHitbox.width, enemyHitbox.height);
            shapeRenderer.rect(enemyAttackHB.x, enemyAttackHB.y, enemyAttackHB.width, enemyAttackHB.height);
        }

        shapeRenderer.end();
    }
}
