package btck.com.model.entity.enemy;

import btck.com.GameManager;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Mushroom extends Enemy {

    TextureAtlas textureAtlas;

    ShapeRenderer shapeRenderer;

    Animation<TextureRegion>[] animations;
    int animationIdx;

    float FRAME_SPEED = 0.1f;
    float statetime;
    int SPEED = 100;
    private float a, b, x1, y1 ,deltaSP;

    int frameToDealDamage = 6;
    boolean dealed;

    public Mushroom(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        hitEntities = new Array<>();
        takeDameEntities = new Array<>();
        damage = 2;
        health = 4;
        exp = 2;
        width = 128;
        height = 160;

        textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/enemy/mushroom.atlas"));
        animations = new Animation[5];

        hitbox = new Rectangle(0, 0, 80, 64);

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_spawn"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_idle"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_run"));
        animations[3] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_die"));
        animations[4] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("spr_attack"));
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
//        System.out.println(x + " " + y);

        statetime += Gdx.graphics.getDeltaTime();

//        shapeRenderer.begin();
//        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width / 2, hitbox.height / 2);
//        shapeRenderer.end();

        spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? 62 : -62) + x, y, (flip ? -1 : 1) * width, height);

        hitbox.x = x;
        hitbox.y = y;

        if(attacking && !dealed && animations[animationIdx].getKeyFrameIndex(statetime) == frameToDealDamage){
//            System.out.println("deal");
            dealDamage();
        }

        if((animationIdx == 4 || animationIdx == 0) && animations[animationIdx].isAnimationFinished(statetime)){
            animationIdx = 2;
            if(attacking){
                hitEntities.clear();
                attacking = false;

                dealed = false;
            }
        }

        if(dead && animations[animationIdx].isAnimationFinished(statetime)) exist = false;

        if(animationIdx > 0 && animationIdx < 3){
//            System.out.println("PL: " + GameManager.getInstance().getCurrentPlayer().getX() + " " + GameManager.getInstance().getCurrentPlayer().getY());
            move(GameManager.getInstance().getCurrentPlayer().getX(), GameManager.getInstance().getCurrentPlayer().getY());
        }
        if(!dead) update();
    }

    @Override
    public void update() {
        if(health <= 0){
            System.out.println("died");
            dead = true;
            statetime = 0;
            animationIdx = 3;
        }
    }

    void dealDamage(){
        dealed = true;
        takeDameEntities = hitEntities;

        for(Iterator<Entity> entityIterator = takeDameEntities.iterator(); entityIterator.hasNext(); ){
            entityIterator.next().takeDamage(this.damage);
        }

        hitEntities.clear();
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
        animationIdx = 4;
        statetime = 0;
        attacking = true;
    }
}
