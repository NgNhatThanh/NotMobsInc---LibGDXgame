package btck.com.common.io.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ConstantSound {
    private static float soundVolume = 0.5f;
    public static Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal("sound\\bgmusic\\Hitman(chosic.com).mp3"));
    public static Sound slash = Gdx.audio.newSound(Gdx.files.internal("sound\\sound ingame\\metal-blade-slice-76-200891.mp3"));

    public static Music bgm = Gdx.audio.newMusic(Gdx.files.internal("sound\\bgmusic\\makai-symphony-dragon-slayer(chosic.com).mp3"));
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
