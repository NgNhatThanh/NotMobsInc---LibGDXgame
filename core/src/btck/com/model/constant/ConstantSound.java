package btck.com.model.constant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ConstantSound {
    public static Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal("sound\\bgmusic\\Hitman(chosic.com).mp3"));
    public static Music slash = Gdx.audio.newMusic(Gdx.files.internal("sound\\sound ingame\\metal-blade-slice-76-200891.mp3"));

    public static Music bgm = Gdx.audio.newMusic(Gdx.files.internal("sound\\bgmusic\\makai-symphony-dragon-slayer(chosic.com).mp3"));
    public static void dispose(){
        bgm.dispose();
        bgmIngame.dispose();
        slash.dispose();
    }
}
