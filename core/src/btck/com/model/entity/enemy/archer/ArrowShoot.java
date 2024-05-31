package btck.com.model.entity.enemy.archer;

import btck.com.MyGdxGame;
import btck.com.controller.attack.Attack;
import btck.com.controller.attack.Bullet;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.io.Constants;
import btck.com.model.entity.Entity;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import screens.IngameScreen;

public class ArrowShoot extends Attack {

    Texture arrow;
    Array<Bullet> arrows;
    int arrowSpeed = 850;

    int frameToShoot;
    boolean shoot = true;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public ArrowShoot(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        shapeRenderer.setAutoShapeType(true);
        damage = 4;
        currentDamage = damage;

        frameToShoot = 6;
        arrows = new Array<>();
        arrow = new Texture(Gdx.files.internal(Constants.ARROW_IMG_PATH));
        hitbox = new Rectangle();
        hitbox.width = arrow.getWidth();
        hitbox.height = arrow.getHeight();
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
        shoot = false;
        owner.setFlip(!(owner.getX() < owner.getAttackX()));
    }

    @Override
    public void update(float statetime) {
        if(!shoot && animation.getKeyFrameIndex(statetime) == frameToShoot){
            arrows.add(new Bullet(owner.getX(), owner.getY() + owner.getHeight() / 2, owner.getAttackX(), owner.getAttackY(), arrowSpeed, this.hitbox.width, this.hitbox.height));
            shoot = true;
        }
        updateHitbox();
    }

    @Override
    public void addHitEntity(Entity entity) {
        if(owner.isDead() || hitEntities.contains(entity, false)) return;
        IngameScreen.addTopEffect(new Slice(entity.getX() - 125, entity.getY() + entity.getHeight() / 2, 45, SLICE_COLOR.WHITE));
        entity.takeDamage(this.currentDamage);
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
        for(Bullet thisFireBall : arrows){
            thisFireBall.move();
            Rectangle thisHitbox = thisFireBall.getHitbox();

            if(thisHitbox.x < 0 || thisHitbox.x > Constants.SCREEN_WIDTH || thisHitbox.y < 0
               || thisHitbox.y > Constants.SCREEN_HEIGHT) arrows.removeValue(thisFireBall, false);
            else MyGdxGame.batch.draw(arrow,
                    thisHitbox.x,
                    thisHitbox.y,
                    thisHitbox.width / 2,
                    thisHitbox.height / 2,
                    thisHitbox.width,
                    thisHitbox.height,
                    1,
                    1,
                    thisFireBall.getRotation(),
                    0,
                    0,
                    (int)thisHitbox.width,
                    (int)thisHitbox.height,
                    thisFireBall.isFlip(),
                    false);
        }
    }

    public boolean hit(Entity entity){

        for(Bullet thisFireBall : arrows){
            if(thisFireBall.getHitbox().overlaps(entity.getHitbox())){
                arrows.removeValue(thisFireBall, false);
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
