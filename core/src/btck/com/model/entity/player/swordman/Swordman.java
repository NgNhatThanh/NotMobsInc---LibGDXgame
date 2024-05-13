package btck.com.model.entity.player.swordman;

import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Swordman extends Player {

    final int NORMAL_SPEED = 200;

    final float FRAME_SPEED = 0.1f;

    private float a, b, x1, y1 ,deltaSP;

    ShapeRenderer shapeRenderer;

    public Swordman(){
        nextLevelExp = 5;
        expToLevelUp = 6;
        exist = true;

        normalSpeed = NORMAL_SPEED;
        currentSpeed = normalSpeed;
        health = 10;
        width = 124;
        height = 84;

        hitbox = new Rectangle(x, y, 124, 84);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        textureAtlas = new TextureAtlas(Constants.swordmanAtlasPath);
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
//        shapeRenderer.begin();
//        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width / 2, hitbox.height / 2);
//        shapeRenderer.end();

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? 62 : -62) + x, y, (flip ? -1 : 1) * width, height);
        hitbox.x = x;
        hitbox.y = y;

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
        if(health <= 0){
            dead = true;
            statetime = 0;
            animationIdx = 3;
        }
        else{
            while(currentExp >= expToLevelUp){
                expToLevelUp += nextLevelExp;
                nextLevelExp += 5;
                ++level;
            }
        }
    }

    public void attack(int x, int y) {
        if(!attacking)
            ConstantSound.slash.play(ConstantSound.getSoundVolume());

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
}