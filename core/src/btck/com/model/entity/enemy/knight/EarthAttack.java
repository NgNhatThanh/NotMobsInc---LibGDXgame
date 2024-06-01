package btck.com.model.entity.enemy.knight;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import btck.com.view.effect.AirStrike;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import btck.com.view.screens.IngameScreen;

public class EarthAttack extends Attack {

    private boolean effAdded = false;

    public EarthAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = new Rectangle();
        hitbox.width = 200;
        hitbox.height = owner.getHitbox().height * 1.5f;
        frameToDealDamage = new int[1];
        frameToDealDamage[0] = 10;
        damage = 10;
        currentDamage = damage;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
    }

    @Override
    public void update(float statetime) {
        if (!effAdded && animation.getKeyFrameIndex(statetime) >= 10) {
            IngameScreen.addTopEffect(new AirStrike(owner.getX(), owner.getY()));
            effAdded = true;
        }
    }

    @Override
    public void addHitEntity(Entity entity) {
        if(hitEntities.contains(entity, false)) return;
        if(animation.getKeyFrameIndex(owner.getStatetime()) >= frameToDealDamage[0]){
            IngameScreen.addTopEffect(new Slice(entity.getX() - 125, entity.getY() + entity.getHeight() / 2, 45, SLICE_COLOR.WHITE));
            hitEntities.add(entity);
            entity.takeDamage(this.currentDamage);
        }
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - 100;
        hitbox.y = owner.getY() - 50;
    }

    public void end(){
        super.end();
        effAdded = false;
    }
}
