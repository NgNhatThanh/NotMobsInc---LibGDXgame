package btck.com.model.entity.enemy.mushroom;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeadAttack extends Attack {

    public HeadAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = owner.getHitbox();
        frameToDealDamage = new int[1];
        frameToDealDamage[0] = 7;
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
        statetime += Gdx.graphics.getDeltaTime();
//        System.out.println("add " + animation.getKeyFrameIndex(statetime));
        if(frameToDealDamageIdx >= frameToDealDamage.length || animation.getKeyFrameIndex(statetime) != frameToDealDamage[frameToDealDamageIdx]) return;
        if(hitEntities.contains(entity, false)) return;
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {

    }
}
