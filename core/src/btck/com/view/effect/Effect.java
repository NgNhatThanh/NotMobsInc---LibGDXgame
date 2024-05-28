package btck.com.view.effect;

import btck.com.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Effect {

    Animation<TextureRegion> ani;
    float x, y, width, heigth, angle;
    float FRAME_DURATION;
    float currentTime = 0;
    Texture sample;

    public Effect(float x, float y, float angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void draw(){
        currentTime += Gdx.graphics.getDeltaTime();
        MyGdxGame.batch.draw(ani.getKeyFrame(currentTime, false),
                x,
                y,
                width / 2,
                heigth / 2,
                width,
                heigth,
                1,
                1,
                angle);
    }

    public boolean isFinished(){
        return ani.isAnimationFinished(currentTime);
    }

}
