package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Player;
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

    private float a, b, x1, y1 ,deltaSP;

    public Ghost(){
        nextLevelExp = 5;
        expToLevelUp = 6;
        exist = true;

        sampleTexture = new Texture(Constants.GHOST_1_SAMPLE_TT_PATH);

        normalSpeed = NORMAL_SPEED;
        currentSpeed = normalSpeed;
        health = 100;
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

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        if(!dead){
            if(!attacking){
                move(Gdx.input.getX(), Constants.screenHeight - Gdx.input.getY());
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
        if(health > 0){
            while(currentExp >= expToLevelUp){
                expToLevelUp += nextLevelExp;
                nextLevelExp += 5;
                ++level;
            }
        }
    }

    public void attack(int x, int y) {
        if(!attacking)
            ConstantSound.getInstance().slash.play(ConstantSound.getInstance().getSoundVolume());

        if(!dead && !attacking){
            attackX = x; attackY = Constants.screenHeight - y;
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
            return;
        }
        else if(!attacking) animationIdx = 1;

        if(desX < x) flip = false;
        else flip = true;

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
}