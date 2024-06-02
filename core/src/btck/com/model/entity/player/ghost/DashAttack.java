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
import com.badlogic.gdx.math.Vector2;
import btck.com.view.screens.IngameScreen;

public class DashAttack extends Attack {

    final int DASH_SPEED = 800;

    float attackX, attackY;

    float angle;

    float dashDistance = 250;

    float curDashDistance = 0;

    float startX, startY;

    public DashAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = new Rectangle();
        hitbox.width = owner.width;
        hitbox.height = owner.height / 2f;
        damage = 2;
        currentDamage = damage;
        owner.attackSpeed = DASH_SPEED;
    }

    @Override
    public void start() {
        curDashDistance = 0;
        startX = owner.getX();
        startY = owner.getY();
        owner.setVulnerable(false);
        owner.currentSpeed = owner.attackSpeed;
        attackX = owner.getAttackX();
        attackY = owner.getAttackY();
        this.angle = owner.getAngle();
        owner.move(attackX, attackY);
    }

    @Override
    public void update(float statetime) {
        curDashDistance = Vector2.len(owner.getX() - startX, owner.getY() - startY);
        if(curDashDistance > dashDistance) owner.setCurrentSpeed(0);
    }

    public void addHitEntity(Entity entity){
        if(animation.getKeyFrameIndex(owner.getStatetime()) < 6){
            updateHitbox();
            if(hitEntities.contains(entity, false)) return;
            entity.takeDamage(this.currentDamage);
            if(currentDamage > 0) IngameScreen.addTopEffect(new Slice(entity.getX() - 125, entity.getY() + entity.getHeight() / 2, owner.getAngle(), SLICE_COLOR.RED));
            if(entity.isDead()) ((Player) owner).currentExp += ((Enemy)entity).exp;
            hitEntities.add(entity);
        }
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
