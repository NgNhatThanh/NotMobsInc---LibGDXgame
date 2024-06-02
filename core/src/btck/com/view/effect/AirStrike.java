package btck.com.view.effect;

import btck.com.common.Constants;
import btck.com.common.sound.ConstantSound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AirStrike extends Effect{

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/effect/air-strike/air-strike.atlas"));

    static Sound SFX = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/air-strike.mp3"));

    public AirStrike(float x, float y) {
        super(x, y, 0);
        this.x -= 140;
        this.y -= 35;
        this.FRAME_DURATION = Constants.FRAME_DURATION[1];
        ani = new Animation<>(FRAME_DURATION, atlas.findRegions("air-strike"));
        SFX.play(ConstantSound.getInstance().getSoundVolume());
    }
}
