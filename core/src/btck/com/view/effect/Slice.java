package btck.com.view.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Slice extends Effect{

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/effect/slice/slice.atlas"));

    public Slice(float x, float y, float angle){
        super(x, y, angle);
        this.FRAME_DURATION = 0.03f;
        ani = new Animation<>(FRAME_DURATION, atlas.findRegions("slice"));
        sample = new Texture(Gdx.files.internal("atlas/effect/slice/slice_sample.png"));
        System.out.println(width + " " + heigth);
        width = sample.getWidth();
        heigth = sample.getHeight();
        sample.dispose();
    }
}
