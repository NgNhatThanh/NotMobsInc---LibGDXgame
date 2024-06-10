package btck.com.model.entity.enemy.knight;

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

public class Knight extends Enemy {

    private float a, b, x1, y1 ,deltaSP;

    public Knight(){
        FRAME_DURATION = Constants.FRAME_DURATION[0];

        attackRange = 130;
        currentHealth = 6;
        exp = 4;

        normalSpeed = 100;
        currentSpeed = 100;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.KNIGHT_ATLAS_PATH));
        animations = new Animation[5];

        hitbox = new Rectangle(0, 0, width, height);

        animations[0] = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("spr_spawn"));
        animations[1] = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("spr_idle"));
        animations[2] = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("spr_run"));
        animations[3] = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("spr_die"));
        animations[4] = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("spr_attack"));

        attack = new EarthAttack(animations[4], this, DEAL_DAMAGE_TIME.ONCE);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
//        System.out.println(health);
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
    public void move(float desX, float desY){
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

    @Override
    public void attack(int x, int y) {
        animationIdx = 4;
        statetime = 0;
        attacking = true;
        attack.start();
    }
}
