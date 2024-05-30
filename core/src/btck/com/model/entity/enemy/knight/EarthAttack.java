package btck.com.model.entity.enemy.knight;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class EarthAttack extends Attack {

    public EarthAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = new Rectangle();
        hitbox.width = 200;
        hitbox.height = owner.getHitbox().height;
        frameToDealDamage = new int[1];
        frameToDealDamage[0] = 10;
        damage = 2;
        currentDamage = damage;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
    }

    @Override
    public void update(float statetime) {

    }

    @Override
    public void addHitEntity(Entity entity) {
        if(hitEntities.contains(entity, false)) return;
        if(animation.getKeyFrameIndex(owner.getStatetime()) >= frameToDealDamage[0]){
            hitEntities.add(entity);
            entity.takeDamage(this.currentDamage);
        }
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - 100;
        hitbox.y = owner.getY() - 50;
    }
}
