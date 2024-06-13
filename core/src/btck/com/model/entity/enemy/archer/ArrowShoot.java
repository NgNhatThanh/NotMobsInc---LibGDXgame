package btck.com.model.entity.enemy.archer;

import btck.com.MyGdxGame;
import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.Attack;
import btck.com.controller.attack.Bullet;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.Constants;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import btck.com.view.screens.IngameScreen;

public class ArrowShoot extends Attack {

    static Sound sfx = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/arrow shot.mp3"));

    Texture arrow;
    int arrowSpeed = 850;

    int frameToShoot;
    boolean shoot = true;

    public ArrowShoot(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        damage = 4 + ((Enemy) owner).bonusDamage;
        currentDamage = damage;

        frameToShoot = 6;
        arrow = new Texture(Gdx.files.internal(Constants.ARROW_IMG_PATH));
        hitbox = new Rectangle();
        hitbox.width = arrow.getWidth();
        hitbox.height = arrow.getHeight();
        owner.attackSpeed = owner.normalSpeed;
    }

    @Override
    public void start() {
        owner.currentSpeed = 0;
        shoot = false;
        owner.setFlip(!(owner.getX() < owner.getAttackX()));
    }

    @Override
    public void update(float statetime) {
        if(!owner.isDead() && !shoot && animation.getKeyFrameIndex(statetime) == frameToShoot){
            IngameScreen.addBullet(new Bullet(arrow,
                    this.damage,
                    owner.getX(),
                    owner.getY() + owner.getHeight() / 2,
                    owner.getAttackX(),
                    owner.getAttackY(),
                    arrowSpeed,
                    this.hitbox.width,
                    this.hitbox.height));
            sfx.play(ConstantSound.getInstance().getSoundVolume());
            shoot = true;
        }
    }

    @Override
    public void addHitEntity(Entity entity) {
        if(owner.isDead() || hitEntities.contains(entity, false)) return;
        IngameScreen.addTopEffect(new Slice(entity.getX(), entity.getY(), 45, owner.getHeight(), SLICE_COLOR.WHITE));
        entity.takeDamage(this.currentDamage);
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
    }

    public boolean hit(Entity entity){
        return false;
    }

    public void end(){
        super.end();
        shoot = true;
    }
}
