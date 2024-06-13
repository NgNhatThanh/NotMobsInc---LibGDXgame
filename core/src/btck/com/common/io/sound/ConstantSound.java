package btck.com.common.io.sound;

import btck.com.common.io.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class ConstantSound {

    @Setter
    private float soundVolume = 0.5f;
    @Setter
    private float bgmVolume = 0.5f;
    public Music bgmIngame = Gdx.audio.newMusic(Gdx.files.internal(Constants.INGAME_BGM_PATH));
    public Sound slash = Gdx.audio.newSound(Gdx.files.internal(Constants.ATTACK_SFX_PATH));
    public Music bgmMenu = Gdx.audio.newMusic(Gdx.files.internal(Constants.MENU_BGM_PATH));
    public Sound playerHitSFX = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/player-hit.mp3"));
    public Sound[] enemyHitSFX;
    private Map<Sound, List<Long>> soundIdsMap; // Bản đồ để lưu trữ ID âm thanh đang chơi
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
        soundIdsMap = new HashMap<>();
    }

    public void dispose(){
        bgmMenu.dispose();
        bgmIngame.dispose();
        slash.dispose();
        playerHitSFX.dispose();
        for (Sound sfx : enemyHitSFX) {
            sfx.dispose();
        }
    }

    public void playEnemyHitSound(){
        int idx = rand.nextInt(3);
        long id = enemyHitSFX[idx].play(soundVolume);
        soundIdsMap.computeIfAbsent(enemyHitSFX[idx], k -> new ArrayList<>()).add(id);
    }

    public void stopEnemyHitSounds() {
        for (Sound sfx : enemyHitSFX) {
            List<Long> ids = soundIdsMap.get(sfx);
            if (ids != null) {
                for (Long id : ids) {
                    sfx.stop(id);
                }
                ids.clear();
            }
        }
    }

    public void playPlayerHitSFX(){
        long id = playerHitSFX.play(soundVolume);
        soundIdsMap.computeIfAbsent(playerHitSFX, k -> new ArrayList<>()).add(id);
    }

    public void setSfxVolume(float volume) {
        soundVolume = volume;
    }
}
