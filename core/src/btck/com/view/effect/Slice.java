package btck.com.view.effect;

import btck.com.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Slice {

    Animation<TextureRegion> ani;
    float FRAME_DURATION = 0.03f;
    float statetime = 0;
    float x, y, angle;

    public Slice(Array<TextureAtlas.AtlasRegion> regionArray, float x, float y, float angle){
        ani = new Animation<>(FRAME_DURATION, regionArray);
        this. x = x;
        this.y = y;
        this.angle = angle;
    }

    public void draw(){
        statetime += Gdx.graphics.getDeltaTime();
        MyGdxGame.batch.draw(ani.getKeyFrame(statetime, false),
                x,
                y,
                ani.getKeyFrame(statetime).getRegionWidth() / 2,
                ani.getKeyFrame(statetime).getRegionHeight() / 2,
                ani.getKeyFrame(statetime).getRegionWidth(),
                ani.getKeyFrame(statetime).getRegionHeight(),
                1,
                1,
                angle);
    }

    public boolean isFinish(){
        return ani.isAnimationFinished(statetime);
    }

}
