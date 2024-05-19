package btck.com.model.entity.enemy.archer;

import btck.com.MyGdxGame;
import btck.com.controller.attack.Attack;
import btck.com.controller.attack.Bullet;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class ArrowShoot extends Attack {

    Texture arrow;
    Array<Bullet> fireballs;
    int fireballSpeed = 600;

    int frameToShoot;
    boolean shoot = true;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public ArrowShoot(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        shapeRenderer.setAutoShapeType(true);

        damage = 3;

        frameToShoot = 6;
        fireballs = new Array<>();
        arrow = new Texture(Gdx.files.internal(Constants.arrowImgPath));
        hitbox = new Rectangle();
        hitbox.width = arrow.getWidth();
        hitbox.height = arrow.getHeight();
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
        shoot = false;
    }

    @Override
    public void update(float statetime) {
        if(!shoot && animation.getKeyFrameIndex(statetime) == frameToShoot){
            fireballs.add(new Bullet(owner.getX(), owner.getY(), owner.getAttackX(), owner.getAttackY(), fireballSpeed, this.hitbox.width, this.hitbox.height));
            shoot = true;
        }
        updateHitbox();
    }

    @Override
    public void addHitEntity(Entity entity) {
        if(owner.isDead() || hitEntities.contains(entity, false)) return;
        entity.takeDamage(this.damage);
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
        for(Bullet thisFireBall : fireballs){
            thisFireBall.move();
            Rectangle thisHitbox = thisFireBall.getHitbox();

            if(thisHitbox.x < 0 || thisHitbox.x > Constants.screenWidth || thisHitbox.y < 0
               || thisHitbox.y > Constants.screenHeight) fireballs.removeValue(thisFireBall, false);
            else MyGdxGame.batch.draw(arrow, thisHitbox.x, thisHitbox.y);

//            shapeRenderer.begin();
//            shapeRenderer.rect(thisHitbox.x, thisHitbox.y, hitbox.width, hitbox.height);
//            shapeRenderer.end();
        }
    }

    public boolean hit(Entity entity){

        for(Bullet thisFireBall : fireballs){
            if(thisFireBall.getHitbox().overlaps(entity.getHitbox())){
                fireballs.removeValue(thisFireBall, false);
                hitEntities.clear();
                return true;
            }
        }
        return false;
    }

    public void end(){
        super.end();
        shoot = true;
    }
}
