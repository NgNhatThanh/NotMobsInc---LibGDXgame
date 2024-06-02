package btck.com.view.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Slice extends Effect{

    static TextureAtlas redSliceAtlas = new TextureAtlas(Gdx.files.internal("atlas/effect/slice/red-slice.atlas"));
    static TextureAtlas whiteSliceAtlas = new TextureAtlas(Gdx.files.internal("atlas/effect/slice/white-slice.atlas"));

    public Slice(float x, float y, float angle, SLICE_COLOR color){
        super(x, y, angle);
        this.FRAME_DURATION = 0.03f;
        if(color == SLICE_COLOR.RED) ani = new Animation<>(FRAME_DURATION, redSliceAtlas.findRegions("slice"));
        else ani = new Animation<>(FRAME_DURATION, whiteSliceAtlas.findRegions("slice"));
    }
}
