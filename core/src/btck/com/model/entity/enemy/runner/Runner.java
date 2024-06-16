package btck.com.model.entity.enemy.runner;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import static java.lang.Math.*;
import static java.lang.Math.sqrt;

public class Runner extends Enemy {

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.RUNNER_ATLAS_PATH));

    public Runner(){
        super();
        FRAME_DURATION = Constants.FRAME_DURATION[0];
        currentHealth = 5 + bonusHealth;
        exp = 5;

        angle = System.currentTimeMillis() % 360;
        normalSpeed = attackSpeed = 350;
        currentSpeed = normalSpeed;

        float radiusAngle = (float) (angle * PI / 180);
        float tan = abs((float)tan(radiusAngle));
        xSpeed = (float) sqrt((attackSpeed * attackSpeed) / (1 + tan * tan));
        ySpeed = xSpeed * tan;

        animations = new Animation[3];

        hitbox = new Rectangle();

        animations[0] = new Animation<>(FRAME_DURATION, atlas.findRegions("spawn"));
        animations[1] = new Animation<>(FRAME_DURATION, atlas.findRegions("run"));
        animations[2] = new Animation<>(FRAME_DURATION, atlas.findRegions("dead"));

        width = animations[1].getKeyFrame(0).getRegionWidth();
        height = animations[1].getKeyFrame(0).getRegionHeight();

        hitbox.width = width;
        hitbox.height = height / 2;

        attack = new AutoAttack(null, this, DEAL_DAMAGE_TIME.ONCE);
    }

    @Override
    public void draw() {
        if(animationIdx == 3) --animationIdx;
        statetime += Gdx.graphics.getDeltaTime();

        hitbox.x = x - width / 2;
        hitbox.y = y;

        if(dead && animations[animationIdx].isAnimationFinished(statetime)){
            exist = false;
            return;
        }

        MyGdxGame.batch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        if(animationIdx == 0 && animations[animationIdx].isAnimationFinished(statetime)){
            vulnerable = true;
            animationIdx = 1;
            attacking = true;
        }

        if(attacking) move(0, 0);

        if(!dead) update();
    }

    @Override
    public void attack(int x, int y) {

    }

    @Override
    public void move(float desX, float desY) {
        float xDist = xSpeed * Gdx.graphics.getDeltaTime();
        float yDist = ySpeed * Gdx.graphics.getDeltaTime();

        x += xDist;
        y += yDist;

        if(x <= 0 || x + width >= Constants.SCREEN_WIDTH){
            xSpeed *= -1;
            if(x < 0) x = 0;
            else x = Constants.SCREEN_WIDTH - width;
        }

        if(y <= 0 || y + height >= Constants.SCREEN_HEIGHT){
            ySpeed *= -1;
            if(y < 0) y = 0;
            else y = Constants.SCREEN_HEIGHT - height;
        }

        flip = (xSpeed < 0);
    }

    public void setCurrentSpeed(float speed){
        float tmp = currentSpeed / speed;
        currentSpeed = speed;

        xSpeed /= tmp;
        ySpeed /= tmp;
    }
}
