package btck.com.common.io.sound;

import btck.com.model.constant.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class ConstantSound {

    @Setter
    private float soundVolume = 0.5f;
    @Setter
    private float bgmVolume = 0.5f;
    public Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal(Constants.ingameBGMPath));
    public Sound slash = Gdx.audio.newSound(Gdx.files.internal(Constants.attackSFXPath));
    public Music bgm = Gdx.audio.newMusic(Gdx.files.internal(Constants.menuBGMPath));
    public Sound[] enemyHitSFX;
    Random rand = new Random();

    public static ConstantSound constantSound;

    public static ConstantSound getInstance(){
        if(constantSound == null) constantSound = new ConstantSound();
        return constantSound;
    }

    public ConstantSound(){
        enemyHitSFX = new Sound[3];
        enemyHitSFX[0] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_1.mp3"));
        enemyHitSFX[1] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_2.mp3"));
        enemyHitSFX[2] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_3.mp3"));
    }

    public void dispose(){
        bgm.dispose();
        bgmIngame.dispose();
        slash.dispose();
    }
    public void  playEnemyHitSound(){
        int idx = rand.nextInt(3);
        enemyHitSFX[idx].play(soundVolume);
    }
}
