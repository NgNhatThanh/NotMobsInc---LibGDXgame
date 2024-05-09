package btck.com.model.entity.enemy.gladiator;

import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
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
        attackRange = 100;
        health = 2;
        exp = 5;
        width = 64;
        height = 64;

        normalSpeed = 100;
        currentSpeed = 100;

        textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/enemy/gladiator/gladiator.atlas"));
        animations = new Animation[4];

        hitbox = new Rectangle(0, 0, width, height);

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("run"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("attack"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("dead"));
        animations[3] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spawn"));
    }


    @Override
    public void draw(SpriteBatch spriteBatch) {
        statetime += Gdx.graphics.getDeltaTime();

        width = animations[animationIdx].getKeyFrame(statetime).getRegionWidth();
        height = animations[animationIdx].getKeyFrame(statetime).getRegionHeight();

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1: 1) * width, height);

        hitbox.x = x;
        hitbox.y = y;

        attack.update(statetime);

    }

    @Override
    public void update() {

    }

    @Override
    public void attack(int x, int y) {

    }

    @Override
    public void move(float desX, float desY) {
        if(abs(x - desX) < attackRange && abs(y - desY) < attackRange) {
//            attack((int) desX, (int) desY);
            return;
        }

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
