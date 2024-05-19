package btck.com.model.entity.enemy.knight;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
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
        frameToDealDamage[0] = 12;
        dealed = new boolean[1];
        damage = 2;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
    }

    @Override
    public void update(float statetime) {
        if(owner.isDead()) return;
        if(frameToDealDamageIdx < frameToDealDamage.length && animation.getKeyFrameIndex(statetime) == frameToDealDamage[frameToDealDamageIdx]){
            dealDamage();
        }
    }

    @Override
    public void addHitEntity(Entity entity) {
        updateHitbox();
        statetime += Gdx.graphics.getDeltaTime();
//        System.out.println("add " + animation.getKeyFrameIndex(statetime));
        if(frameToDealDamageIdx >= frameToDealDamage.length || animation.getKeyFrameIndex(statetime) != frameToDealDamage[frameToDealDamageIdx]) return;
        if(hitEntities.contains(entity, false)) return;
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - 100;
        hitbox.y = owner.getY() - 50;
    }
}
