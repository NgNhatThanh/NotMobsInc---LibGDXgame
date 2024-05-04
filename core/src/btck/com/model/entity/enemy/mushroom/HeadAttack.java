package btck.com.model.entity.enemy.mushroom;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeadAttack extends Attack {

    public HeadAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        frameToDealDamage = new int[1];
        frameToDealDamage[0] = 7;
        dealed = new boolean[1];
        damage = 2;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
    }

    public void end(){
        super.end();
        owner.currentSpeed = owner.normalSpeed;
    }
}
