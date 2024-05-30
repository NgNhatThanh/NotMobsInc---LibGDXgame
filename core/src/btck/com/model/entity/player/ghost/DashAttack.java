package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.Player;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import screens.IngameScreen;

public class DashAttack extends Attack {

    final int DASH_SPEED = 600;

    float attackX, attackY;

    float angle;

    public DashAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = new Rectangle();
        hitbox.width = owner.width;
        hitbox.height = owner.height / 2f;
        damage = 2;
        currentDamage = damage;
    }

    @Override
    public void start() {
        owner.setVulnerable(false);
        owner.currentSpeed = DASH_SPEED;
        attackX = owner.getAttackX();
        attackY = owner.getAttackY();
        this.angle = owner.getAngle();
        owner.move(attackX, attackY);
    }

    @Override
    public void update(float statetime) {}

    public void addHitEntity(Entity entity){
        updateHitbox();
        if(hitEntities.contains(entity, false)) return;
        entity.takeDamage(this.currentDamage);
        if(currentDamage > 0) IngameScreen.addTopEffect(new Slice(entity.getX() - 125, entity.getY() + entity.getHeight() / 2, owner.getAngle(), SLICE_COLOR.RED));
        if(entity.isDead()) ((Player) owner).currentExp += ((Enemy)entity).exp;
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - owner.getWidth() / 2;
        hitbox.y = owner.getY() + owner.getHeight() / 2;
    }

    public void end(){
        super.end();
        owner.setVulnerable(true);
    }
}
