package btck.com.model.entity.player;

import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.GameConstant;
import btck.com.model.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Swordman extends Player {

    TextureAtlas textureAtlas;
    final int NORMAL_SPEED = 200;
    final int ATTACK_SPEED = 450;
    int CURRENT_SPEED = NORMAL_SPEED;

    final float FRAME_SPEED = 0.1f;
    Animation<TextureRegion>[] animations;
    float statetime;
    int animationIdx;

    private float a, b, x1, y1 ,deltaSP;

    ShapeRenderer shapeRenderer;

    public Swordman(){
        damage = 2;
        nextLevelExp = 5;
        expToLevelUp = 6;
        exist = true;

        health = 5;
        width = 124;
        height = 84;

        hitbox = new Rectangle(x, y, 124, 84);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        textureAtlas = new TextureAtlas("atlas/player/swordman/swordman.atlas");
        animations = new Animation[4];

        animations[0] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_idle"));
        animations[1] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_run"));
        animations[2] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_attack"));
        animations[3] = new Animation<>(FRAME_SPEED,textureAtlas.findRegions("spr_die"));
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

//        System.out.println(hitbox.x + " " + hitbox.y);

        if(dead && animations[animationIdx].isAnimationFinished(statetime)){
            System.out.println(animationIdx);
            exist = false;
            System.out.println("bay mau");
            return;
        }

        if(attacking && animations[animationIdx].isAnimationFinished(statetime)){
            statetime = 0;
            hitEntities.clear();
            CURRENT_SPEED = NORMAL_SPEED;
            animationIdx = 1;
            attacking = false;
        }

//        hitbox.setWidth(animations[animationIdx].getKeyFrame(statetime).getRegionWidth());
//        hitbox.setHeight(animations[animationIdx].getKeyFrame(statetime).getRegionHeight());
//        System.out.println(hitbox.width +  " " + hitbox.getHeight());

        if(!dead){
            if(!attacking){
                move(Gdx.input.getX(), GameConstant.screenHeight - Gdx.input.getY());
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
            attackX = x; attackY = GameConstant.screenHeight - y;
            CURRENT_SPEED = ATTACK_SPEED;
            animationIdx = 2;
            attacking = true;
            statetime = 0;
            move(attackX, attackY);
        }
    }

    @Override
    public void move(int desX, int desY){
        if(abs(x - desX) < 5 && abs(y - desY) < 5) {
            if(!attacking) animationIdx = 0;
            return;
        }
        else if(!attacking) animationIdx = 1;

        if(desX < x) flip = true;
        else flip = false;

        deltaSP = CURRENT_SPEED * Gdx.graphics.getDeltaTime();

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
