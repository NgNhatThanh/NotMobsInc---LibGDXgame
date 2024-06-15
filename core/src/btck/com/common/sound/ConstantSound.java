package btck.com.common.sound;

import btck.com.common.Constants;
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
    private float bgmVolume = 0.5f;
    public Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal(Constants.INGAME_BGM_PATH));
    public Sound slash = Gdx.audio.newSound(Gdx.files.internal(Constants.ATTACK_SFX_PATH));
    public Music bgmMenu = Gdx.audio.newMusic(Gdx.files.internal(Constants.MENU_BGM_PATH));
    public Sound playerHitSFX = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/player-hit.mp3"));
    public Sound[] enemyHitSFX;
    Random rand = new Random();

    public static ConstantSound constantSound;

    public static ConstantSound getInstance(){
        if(constantSound == null) constantSound = new ConstantSound();
        return constantSound;
    }

    public ConstantSound(){
        bgmIngame.setLooping(true);
        bgmMenu.setLooping(true);
        enemyHitSFX = new Sound[3];
        enemyHitSFX[0] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_1.mp3"));
        enemyHitSFX[1] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_2.mp3"));
        enemyHitSFX[2] = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/enemy_hit_3.mp3"));
    }

    public void dispose(){
        bgmMenu.dispose();
        bgmIngame.dispose();
        slash.dispose();
    }
    public void  playEnemyHitSound(){
        int idx = rand.nextInt(3);
        enemyHitSFX[idx].play(soundVolume);
    }
    public void playPlayerHitSFX(){
        playerHitSFX.play(soundVolume);
    }

    public void setBgmVolume(float volume){
        this.bgmVolume = volume;
        this.bgmIngame.setVolume(this.bgmVolume);
        this.bgmMenu.setVolume(this.bgmVolume);
    }
}