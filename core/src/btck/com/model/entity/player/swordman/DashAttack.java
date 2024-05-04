package btck.com.model.entity.player.swordman;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DashAttack extends Attack {

    final int DASH_SPEED = 450;

    float attackX, attackY;

    public DashAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        frameToDealDamage = new int[]{2, 3, 4};
        dealed = new boolean[3];
        damage = 2;
    }

    @Override
    public void start() {
        owner.currentSpeed = DASH_SPEED;
        attackX = owner.getAttackX();
        attackY = owner.getAttackY();

        owner.move(attackX, attackY);
    }

    public void end(){
        super.end();

        owner.currentSpeed = owner.normalSpeed;
    }

}
