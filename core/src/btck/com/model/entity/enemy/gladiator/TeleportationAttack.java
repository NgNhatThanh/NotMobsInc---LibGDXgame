package btck.com.model.entity.enemy.gladiator;

import btck.com.GameManager;
import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class TeleportationAttack extends Attack {

    final int teleportSpeed = 450;
    private float targetX, targetY;
    private float a, b, x1, y1 ,deltaSP;
    int frameToTeleport = 1;

    public TeleportationAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = owner.getHitbox();
        frameToDealDamage = new int[1];
        frameToDealDamage[0] = 3;
        dealed = new boolean[1];
        damage = 2;
        coolDown = 1000;
        lastAttackTime = 0;
    }

    @Override
    public void start() {
        System.out.println(lastAttackTime + " " + System.currentTimeMillis());
        targetX = GameManager.getInstance().getCurrentPlayer().getX();
        targetY = GameManager.getInstance().getCurrentPlayer().getY();
        owner.currentSpeed = teleportSpeed;
    }

    @Override
    public void update(float statetime) {
        if(owner.isDead()) return;

        int currentFrame = animation.getKeyFrameIndex(statetime);
        if(currentFrame > frameToTeleport){
            moveTowardsTarget();
        }

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

    private void moveTowardsTarget() {
        deltaSP = owner.currentSpeed * Gdx.graphics.getDeltaTime();

        if(abs(owner.getX() - targetX) < 5){
            if(targetY > owner.getY()) owner.setY(owner.getY() + deltaSP);
            else owner.setY(owner.getY() - deltaSP);
            return;
        }

        if(abs(owner.getY() - targetY) < 5){
            if(targetX > owner.getX()) owner.setX(owner.getX() + deltaSP);
            else owner.setX(owner.getX() - deltaSP);
            return;
        }

        a = (owner.getY() - targetY) / (owner.getX() - targetX);
        b = owner.getY() - a * owner.getX();

        x1 = owner.getX();
        y1 = owner.getY();
        while(sqrt((x1 - owner.getX()) * (x1 - owner.getX()) + (y1 - owner.getY()) * (y1 - owner.getY())) < deltaSP){
            if(owner.getX() < targetX) x1 += deltaSP / (50 * abs(a));
            else x1 -= deltaSP / (50 * abs(a));
            y1 = a * x1 + b;
        }

        owner.setX(x1);
        owner.setY(y1);
    }

}
