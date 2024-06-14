package btck.com.view.effect;

import btck.com.common.Constants;
import btck.com.common.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AirStrikeCall extends Effect{

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/effect/airstrike-call/airstrike-call.atlas"));

    boolean die;

    public AirStrikeCall(float x, float y, boolean die) {
        super(x, y, 0);
        this.die = die;
        if(die) this.FRAME_DURATION = Constants.FRAME_DURATION[3];
        else this.FRAME_DURATION = Constants.FRAME_DURATION[2];
        this.ani = new Animation<>(FRAME_DURATION, atlas.findRegions("airstrike-call"));
        this.x -= (float) ani.getKeyFrame(0).getRegionWidth() / 2;
    }

    public boolean isFinished(){
        if(super.isFinished()){
            if(die) GameManager.getInstance().getCurrentPlayer().setExist(false);
            return true;
        }
        return false;
    }
}
