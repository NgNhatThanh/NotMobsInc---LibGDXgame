package btck.com.model.entity.enemy.runner;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import btck.com.view.screens.IngameScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DumpAttack extends Attack {

    boolean touched;

    public DumpAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = owner.getHitbox();
        damage = 4 + ((Enemy) owner).bonusDamage;
        currentDamage = damage;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float statetime) {

    }

    @Override
    public void addHitEntity(Entity entity) {
        if(touched) return;
        entity.takeDamage(this.damage);
        IngameScreen.addTopEffect(new Slice(entity.getX(), entity.getY(), 45, owner.getHeight(), SLICE_COLOR.WHITE));
    }

    public boolean hit(Entity entity){
        if(super.hit(entity)) return true;
        touched = false;
        return false;
    }

    @Override
    public void updateHitbox() {
    }
}
