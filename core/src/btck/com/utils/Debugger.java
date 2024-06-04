package btck.com.utils;

import btck.com.common.GameManager;
import btck.com.controller.attack.Bullet;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import btck.com.model.entity.Enemy;
import btck.com.view.screens.IngameScreen;
import com.badlogic.gdx.graphics.Color;
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

    public void debug(){
        Rectangle playerHitbox = GameManager.getInstance().getCurrentPlayer().getHitbox();
        Rectangle playerAttackHB = GameManager.getInstance().getCurrentPlayer().getAttack().getHitbox();

        shapeRenderer.begin();
        shapeRenderer.rect(playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerAttackHB.x, playerAttackHB.y, playerAttackHB.width, playerAttackHB.height, Color.RED, Color.RED, Color.RED, Color.RED);

        for(Bullet bullet : IngameScreen.getBullets()){
            Rectangle hitbox = bullet.getHitbox();
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }

        for(Skill skill : GameManager.getInstance().getCurrentPlayer().getSkills()){
            System.out.println(skill.getState());
            if(skill.getState() == SKILL_STATE.ACTIVE){
                System.out.println("skill debug");
                shapeRenderer.rect(skill.getHitbox().x, skill.getHitbox().y, skill.getHitbox().width, skill.getHitbox().height);
            }
        }

        shapeRenderer.setColor(Color.GREEN);

        for(Enemy enemy : GameManager.getInstance().enemies){
            Rectangle enemyHitbox = enemy.getHitbox();
            Rectangle enemyAttackHB = enemy.getAttack().getHitbox();
            shapeRenderer.rect(enemyHitbox.x, enemyHitbox.y, enemyHitbox.width, enemyHitbox.height);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(enemyAttackHB.x, enemyAttackHB.y, enemyAttackHB.width, enemyAttackHB.height);
            shapeRenderer.setColor(Color.GREEN);
        }

        shapeRenderer.end();
    }
}
