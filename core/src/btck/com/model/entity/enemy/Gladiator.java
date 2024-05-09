package btck.com.model.entity.enemy;

import btck.com.GameManager;
import btck.com.model.entity.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Gladiator extends Enemy {

    TextureAtlas textureAtlas;

    ShapeRenderer shapeRenderer;

    Animation<TextureRegion>[] animations;
    int animationIdx;

    float FRAME_SPEED = 0.1f;
    float stateTime;
    int SPEED = 100;
    private float a, b, x1, y1 ,deltaSP;

    public Gladiator(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        health = 1;

        exp = 2;
        width = 128;
        height = 160;

        textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/enemy/gladiator/gladiator_atlas.atlas"));
        animations = new Animation[3];

        hitbox = new Rectangle(0, 0, 80, 64);

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("Run"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("Attack"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("Dead"));
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        stateTime += Gdx.graphics.getDeltaTime();
        spriteBatch.draw(animations[animationIdx].getKeyFrame(stateTime, true), (flip ? -62 : 62) + x, y, (flip ? 1 : -1) * width, height);

        hitbox.x = x;
        hitbox.y = y;

        if((animationIdx == 1) && animations[animationIdx].isAnimationFinished(stateTime))
                animationIdx = 0;

        if(dead && animations[animationIdx].isAnimationFinished(stateTime)) {
            exist = false;
        }
        if(animationIdx >= 0 && animationIdx < 3){
            move(GameManager.getInstance().getCurrentPlayer().getX(), GameManager.getInstance().getCurrentPlayer().getY());
        }
        if(!dead) update();
    }

    @Override
    public void update() {
        if(health <= 0){
            animationIdx = 2;
            System.out.println("died");
            dead = true;
            stateTime = 0f;
        }
    }

    @Override
    public void move(float desX, float desY){
        if(abs(x - desX) < 50 && abs(y - desY) < 50) {
            attack((int) desX, (int) desY);
            return;
        }

        if(desX < x) flip = false;
        else flip = true;

        deltaSP = SPEED * Gdx.graphics.getDeltaTime();

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
        animationIdx = 1;
        stateTime = 0;
        attacking = true;
    }
}