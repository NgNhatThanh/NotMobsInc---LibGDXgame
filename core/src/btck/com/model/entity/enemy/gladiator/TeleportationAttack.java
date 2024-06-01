package btck.com.model.entity.enemy.gladiator;

import btck.com.GameManager;
import btck.com.common.io.sound.ConstantSound;
import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Entity;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import screens.IngameScreen;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class TeleportationAttack extends Attack {

    static Sound[] sfx = {Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/gladiator attack 1.mp3")),
                        Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/gladiator attack 2.mp3"))};
    static Random rnd = new Random();
    int sfxIdx;

    final int teleportSpeed = 450;
    private boolean sfxPlayed = false;
    private float targetX, targetY;
    private float a, b, x1, y1 ,deltaSP;
    int frameToTeleport = 2;

    public TeleportationAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        hitbox = new Rectangle();
        hitbox.width = owner.width;
        hitbox.height = owner.height;
        damage = 6;
        currentDamage = damage;
        coolDown = 1000;
        lastAttackTime = 0;
        sfxIdx = rnd.nextInt(2);
    }

    @Override
    public void start() {
        targetX = GameManager.getInstance().getCurrentPlayer().getX();
        targetY = GameManager.getInstance().getCurrentPlayer().getY();
        currentDamage = 0;
        owner.setFlip(!(targetX > owner.getX()));
        owner.currentSpeed = teleportSpeed;
    }

    @Override
    public void update(float statetime) {
        if(owner.isDead()) return;
        int currentFrame = animation.getKeyFrameIndex(statetime);
        if(currentFrame > frameToTeleport){
            currentDamage = damage;
            if(!sfxPlayed){
                sfx[sfxIdx].play(ConstantSound.getInstance().getSoundVolume());
                sfxPlayed = true;
            }
            moveTowardsTarget();
        }
    }

    @Override
    public void addHitEntity(Entity entity) {
        int currentFrame = animation.getKeyFrameIndex(owner.getStatetime());
        if(currentFrame > frameToTeleport){
            updateHitbox();
            if(hitEntities.contains(entity, false)) return;
            IngameScreen.addTopEffect(new Slice(entity.getX() - 125, entity.getY() + entity.getHeight() / 2, 45, SLICE_COLOR.WHITE));
            entity.takeDamage(this.currentDamage);
            hitEntities.add(entity);
        }
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - owner.getWidth() / 2;
        hitbox.y = owner.getY();
    }

    private void moveTowardsTarget() {
        deltaSP = owner.currentSpeed * Gdx.graphics.getDeltaTime();

        if(abs(owner.getX() - targetX) < 5 && abs(owner.getY() - targetY) < 5) return;

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

    public void end(){
        super.end();
        sfxPlayed = false;
    }
}
