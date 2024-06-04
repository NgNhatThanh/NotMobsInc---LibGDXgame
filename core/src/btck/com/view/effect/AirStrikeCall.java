package btck.com.view.effect;

import btck.com.common.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ConcurrentModificationException;

public class AirStrikeCall extends Effect{

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/effect/airstrike-call/airstrike-call.atlas"));

    public AirStrikeCall(float x, float y) {
        super(x, y, 0);
        this.FRAME_DURATION = Constants.FRAME_DURATION[2];
        this.ani = new Animation<>(FRAME_DURATION, atlas.findRegions("airstrike-call"));
        this.x -= ani.getKeyFrame(0).getRegionWidth() / 2;
    }
}
