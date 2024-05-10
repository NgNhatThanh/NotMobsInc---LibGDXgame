package btck.com.common.io.sound;

import btck.com.model.constant.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ConstantSound {
    private static float soundVolume = 1.0f;
    public static Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal(Constants.ingameBGMPath));
    public static Sound slash = Gdx.audio.newSound(Gdx.files.internal(Constants.attackSFXPath));

    public static Music bgm = Gdx.audio.newMusic(Gdx.files.internal(Constants.menuBGMPath));
    public static void dispose(){
        bgm.dispose();
        bgmIngame.dispose();
        slash.dispose();
    }

    public static float getSoundVolume() {
        return soundVolume;
    }

    public static void setSoundVolume(float soundVolume) {
        ConstantSound.soundVolume = soundVolume;
    }
}
