package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.io.sound.ConstantSound;
import btck.com.common.io.Constants;
import btck.com.model.entity.Player;
import btck.com.model.entity.player.Blinking;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Ghost extends Player {

    final int NORMAL_SPEED = 200;

    final float FRAME_SPEED = 0.1f;

    private float tan, deltaSP;

    public Ghost(){
        vulnerable = true;
        nextLevelExp = 5;
        expToLevelUp = 6;
        exist = true;

        sampleTexture = new Texture(Constants.GHOST_1_SAMPLE_TT_PATH);

        normalSpeed = NORMAL_SPEED;
        currentSpeed = normalSpeed;
        currentHealth = 100;
        maxHealth = 100;
        width = sampleTexture.getWidth();
        height = sampleTexture.getHeight();

        hitbox = new Rectangle(x, y, width, height);

        textureAtlas = new TextureAtlas(Constants.GHOST_1_ATLAS_PATH);
        animations = new Animation[4];

        animations[0] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_idle"));
        animations[1] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_run"));
        animations[2] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_attack"));
        animations[3] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_die"));

        attack = new DashAttack(animations[2], this, DEAL_DAMAGE_TIME.ONCE);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        statetime += Gdx.graphics.getDeltaTime();

        width = animations[animationIdx].getKeyFrame(statetime).getRegionWidth();
        height = animations[animationIdx].getKeyFrame(statetime).getRegionHeight();

        hitbox.x =  x - width / 2 + 10;
        hitbox.y = y;

        hitbox.width = width - 10;
        hitbox.height = height / 2;

        if(dead && animations[animationIdx].isAnimationFinished(statetime)){
            exist = false;
            System.out.println("bay mau");
            return;
        }

        if(attacking && animations[animationIdx].isAnimationFinished(statetime)){
            statetime = 0;
            animationIdx = 1;
            attacking = false;
            attack.end();
        }

        if(attacking) attack.update(statetime);

        if(Blinking.blinking) Blinking.update();

        if(Blinking.appearing) spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        if(!dead){
            if(!attacking){
                move(Gdx.input.getX(), Constants.SCREEN_HEIGHT - Gdx.input.getY());
            }
            else{
                move(attackX, attackY);
            }
        }

        if(!dead) update();
    }

    @Override
    public void update() {
        super.update();
        if(currentHealth > 0){
            while(currentExp >= expToLevelUp){
                expToLevelUp += nextLevelExp;
                nextLevelExp += 5;
                ++level;
            }
        }
    }

    public void attack(int x, int y) {
        if(!attacking) ConstantSound.getInstance().slash.play(ConstantSound.getInstance().getSoundVolume());

        if(!dead && !attacking){
            attackX = x; attackY = Constants.SCREEN_HEIGHT - y;
            animationIdx = 2;
            attacking = true;
            statetime = 0;
            attack.start();
        }
    }

    @Override
    public void move(float desX, float desY){
        if(abs(x - desX) < 5 && abs(y - desY) < 5) {
            if(!attacking) animationIdx = 0;
            angle = 0;
            return;
        }
        else if(!attacking) animationIdx = 1;

        if(desX < x) flip = false;
        else flip = true;

        deltaSP = currentSpeed * Gdx.graphics.getDeltaTime();

        if(abs(x - desX) < 5){
            if(desY > y) y += deltaSP;
            else y -= deltaSP;
            angle = 0;
            return;
        }

        if(abs(y - desY) < 5){
            if(desX > x) x += deltaSP;
            else x -= deltaSP;
            angle = 90;
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
}