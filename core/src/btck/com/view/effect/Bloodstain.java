package btck.com.view.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

public class Bloodstain extends Effect{

    static TextureAtlas[] atlases = {new TextureAtlas(Gdx.files.internal("atlas/effect/bloodstain/blood-stain1.atlas")),
                                          new TextureAtlas(Gdx.files.internal("atlas/effect/bloodstain/blood-stain2.atlas")),
                                          new TextureAtlas(Gdx.files.internal("atlas/effect/bloodstain/blood-stain3.atlas"))};

    static Random rnd = new Random();
    float timeToDisappear;

    public Bloodstain(float x, float y, float angle){
        super(x, y, angle);
        int idx = rnd.nextInt(atlases.length);
        timeToDisappear = rnd.nextInt(10) + 10;
        this.FRAME_DURATION = timeToDisappear + 1;
        ani = new Animation<>(FRAME_DURATION, atlases[idx].findRegions("blood-stain"));
        sample = new Texture(Gdx.files.internal("atlas/effect/bloodstain/blood-stain_sample.png"));
        width = sample.getWidth();
        heigth = sample.getHeight();
        sample.dispose();
    }

    public void draw(){
        super.draw();
        if(currentTime >= timeToDisappear){
            currentTime = 0;
            ani.setFrameDuration(0.08f);
        }
    }
}
