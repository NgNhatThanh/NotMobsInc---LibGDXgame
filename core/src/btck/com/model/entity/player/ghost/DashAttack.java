package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DashAttack extends Attack {

    final int DASH_SPEED = 600;

    float attackX, attackY;

    public DashAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = owner.getHitbox();
        damage = 2;
    }

    @Override
    public void start() {
        owner.currentSpeed = DASH_SPEED;
        attackX = owner.getAttackX();
        attackY = owner.getAttackY();

        owner.move(attackX, attackY);
    }

    @Override
    public void update(float statetime) {

    }

    public void addHitEntity(Entity entity){
        if(hitEntities.contains(entity, false)) return;
        entity.takeDamage(this.damage);
        if(entity.isDead()) ((Player) owner).currentExp += ((Enemy)entity).exp;
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
    }
}
