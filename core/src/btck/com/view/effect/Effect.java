package btck.com.view.effect;

import btck.com.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;

public abstract class Effect {

    Animation<TextureRegion> ani;
    float x, y, width, heigth, angle;
    @Getter
    float FRAME_DURATION;
    float currentTime = 0;
    boolean fixedSize = true;

    public Effect(float x, float y, float angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void draw(){
        currentTime += Gdx.graphics.getDeltaTime();

        if(fixedSize){
            width = ani.getKeyFrame(currentTime).getRegionWidth();
            heigth = ani.getKeyFrame(currentTime).getRegionHeight();
        }

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

    public void setFRAME_DURATION(float FD){
        this.FRAME_DURATION = FD;
        ani.setFrameDuration(FD);
    }

}
