package btck.com.model.entity.enemy.mage;

import btck.com.GameManager;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Mage extends Enemy {

    float FRAME_SPEED = 0.1f;

    private float a, b, x1, y1 ,deltaSP;

    public Mage(){
        attackRange = 200;
        health = 3;
        exp = 5;
        width = 64;
        height = 64;

        normalSpeed = 100;
        currentSpeed = 100;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.mageAtlasPath));
        animations = new Animation[5];

        hitbox = new Rectangle(0, 0, width, height);

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_spawn"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_idle"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_run"));
        animations[3] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_die"));
        animations[4] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_attack"));

        attack = new FireSpellAttack(animations[4], this, DEAL_DAMAGE_TIME.ONCE);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        statetime += Gdx.graphics.getDeltaTime();

        width = animations[animationIdx].getKeyFrame(statetime).getRegionWidth();
        height = animations[animationIdx].getKeyFrame(statetime).getRegionHeight();

//        shapeRenderer.begin();
//        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width / 2, hitbox.height / 2);
//        shapeRenderer.end();

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        hitbox.x = x;
        hitbox.y = y;

        attack.update(statetime);

        if((animationIdx == 4 || animationIdx == 0) && animations[animationIdx].isAnimationFinished(statetime)){
            animationIdx = 2;

            if(attacking){
                attack.start();
                attack.end();
            }
        }

        if(dead && animations[animationIdx].isAnimationFinished(statetime)) exist = false;

        if(animationIdx > 0 && animationIdx < 3){
            move(GameManager.getInstance().getCurrentPlayer().getX(), GameManager.getInstance().getCurrentPlayer().getY());
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
    }

    @Override
    public void attack(int x, int y) {
        animationIdx = 4;
        statetime = 0;
        attackX = x;
        attackY = y;
        attacking = true;
    }

    @Override
    public void move(float desX, float desY) {
        if(abs(x - desX) < attackRange && abs(y - desY) < attackRange) {
            attack((int) desX, (int) desY);
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
}
