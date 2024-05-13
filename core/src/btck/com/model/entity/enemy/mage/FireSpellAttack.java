package btck.com.model.entity.enemy.mage;

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

public class FireSpellAttack extends Attack {

    Texture fireball;
    Array<Bullet> fireballs;
    int fireballSpeed = 600;

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public FireSpellAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        shapeRenderer.setAutoShapeType(true);

        damage = 3;

        fireballs = new Array<>();
        fireball = new Texture(Gdx.files.internal(Constants.fireballImgPath));
        hitbox = new Rectangle();
        hitbox.width = 32;
        hitbox.height = 32;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
        fireballs.add(new Bullet(owner.getX(), owner.getY(), owner.getAttackX(), owner.getAttackY(), fireballSpeed, this.hitbox.width, this.hitbox.height));
    }

    @Override
    public void update(float statetime) {
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
            else MyGdxGame.batch.draw(fireball, thisHitbox.x, thisHitbox.y);

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

}
