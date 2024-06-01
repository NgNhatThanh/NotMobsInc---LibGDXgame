package btck.com.model.entity.enemy.gladiator;

import btck.com.common.GameManager;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.Constants;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Gladiator extends Enemy {

    float FRAME_SPEED = 0.1f;

    private float a, b, x1, y1 ,deltaSP;

    public Gladiator(){
        attackRange = 200;
        currentHealth = 4;
        exp = 5;
        sampleTexture = new Texture(Constants.GLADIATOR_SAMPLE_TT_PATH);

        width = sampleTexture.getWidth();
        height = sampleTexture.getHeight();
        sampleTexture.dispose();

        normalSpeed = 100;
        currentSpeed = 100;

        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.GLADIATOR_ATLAS_PATH));
        animations = new Animation[5];

        hitbox = new Rectangle(0, 0, width, height / 2);

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spawn"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("idle"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("run"));
        animations[3] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("dead"));
        animations[4] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("attack"));

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

        a = (y - desY) / (x - desX);
        b = y - a * x;

        x1 = x;
        y1 = y;
        while(sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y)) < deltaSP){
            if(x < desX) x1 += deltaSP / (50 * abs(a));
            else x1 -= deltaSP / (50 * abs(a));
            y1 = a * x1 + b;
        }

        x = x1;
        y = y1;
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
