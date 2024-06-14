package btck.com.model.entity.enemy.gladiator;

import btck.com.common.GameManager;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.Constants;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Gladiator extends Enemy {

    private float tan, deltaSP;

    public Gladiator(){
        super();
        FRAME_DURATION = Constants.FRAME_DURATION[0];
        attackRange = 200;
        currentHealth = 5 + bonusHealth;
        exp = 6;

        normalSpeed = 100;
        currentSpeed = normalSpeed;

        atlas = new TextureAtlas(Gdx.files.internal(Constants.GLADIATOR_ATLAS_PATH));
        animations = new Animation[5];

        hitbox = new Rectangle(0, 0, width, height / 2);

        animations[0] = new Animation<>(FRAME_DURATION, atlas.findRegions("spawn"));
        animations[1] = new Animation<>(FRAME_DURATION, atlas.findRegions("idle"));
        animations[2] = new Animation<>(FRAME_DURATION, atlas.findRegions("run"));
        animations[3] = new Animation<>(FRAME_DURATION, atlas.findRegions("dead"));
        animations[4] = new Animation<>(FRAME_DURATION, atlas.findRegions("attack"));

        width = animations[0].getKeyFrame(0).getRegionWidth();
        height = animations[0].getKeyFrame(0).getRegionHeight();

        attack = new TeleportationAttack(animations[4], this, DEAL_DAMAGE_TIME.ONCE);
    }


    @Override
    public void draw(SpriteBatch spriteBatch) {
        statetime += Gdx.graphics.getDeltaTime();

        width = animations[animationIdx].getKeyFrame(statetime).getRegionWidth();
        height = animations[animationIdx].getKeyFrame(statetime).getRegionHeight();

        hitbox.x = x - width / 2;
        hitbox.y = y;

        hitbox.width = width;
        hitbox.height = height / 2;

        if(dead && animations[animationIdx].isAnimationFinished(statetime)){
            exist = false;
            return;
        }

        if(attacking) attack.update(statetime);

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        if((animationIdx == 4 || animationIdx == 0) && animations[animationIdx].isAnimationFinished(statetime)){
            vulnerable = true;
            animationIdx = 2;
            if(attacking){
                attacking = false;
                attack.end();
            }
        }

        if(animationIdx > 0 && animationIdx < 3){
            move(GameManager.getInstance().getCurrentPlayer().getX(), GameManager.getInstance().getCurrentPlayer().getY());
        }
        if(!dead) update();
    }

    @Override
    public void move(float desX, float desY) {
        if(abs(x - desX) < attackRange && abs(y - desY) < attackRange) {
            if(System.currentTimeMillis() - attack.lastAttackTime > attack.coolDown){
                attack((int) desX, (int) desY);
                attack.lastAttackTime = System.currentTimeMillis();
            }
            return;
        }

        if(desX < x) flip = true;
        else flip = false;

        deltaSP = currentSpeed * Gdx.graphics.getDeltaTime();

        if(abs(x - desX) < 5){
            if(desY > y) y += deltaSP;
            else y -= deltaSP;
            return;
        }

        if(abs(y - desY) < 5){
            if(desX > x) x += deltaSP;
            else x -= deltaSP;
            return;
        }

        tan = (y - desY) / (x - desX);

        angle = (float)Math.atan(tan);
        angle = angle * (float)(180 / Math.PI);

        if((angle > 0 && y > desY)
                || (angle < 0 && y < desY)) angle += 180;
        else if(angle < 0) angle += 360;

        xSpeed = (float) sqrt((currentSpeed * currentSpeed) / (1 + tan * tan));
        ySpeed = abs(xSpeed * tan);

        if(angle > 90 && angle < 270) xSpeed *= -1;
        if(angle > 180 && angle < 360) ySpeed *= -1;

        float xDist = xSpeed * Gdx.graphics.getDeltaTime();
        float yDist = ySpeed * Gdx.graphics.getDeltaTime();

        if(desX > x) x = Math.min(desX, x + xDist);
        else x = Math.max(desX, x + xDist);

        if(desY > y) y = Math.min(desY, y + yDist);
        else y = Math.max(desY, y + yDist);
    }

    @Override
    public void attack(int x, int y) {
        if(!dead && !attacking){
            attackX = x; attackY = Constants.SCREEN_HEIGHT - y;
            animationIdx = 4;
            attacking = true;
            statetime = 0;
            attack.start();
        }
    }

}
