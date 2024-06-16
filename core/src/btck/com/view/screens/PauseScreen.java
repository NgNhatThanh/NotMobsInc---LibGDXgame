package btck.com.view.screens;

import btck.com.common.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.common.sound.ConstantSound;
import btck.com.model.state.GameState;
import btck.com.model.entity.player.ghost.Ghost;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class PauseScreen implements Screen {
    private final MyGdxGame myGdxGame;
    private final IngameScreen ingameScreen;
    private final Button btnResume;
    private final Button btnHome;
    private final Button btnRestart;
    private final Button btnMusic, btnSFX;
    private final Texture pauseWindow;
    private final int windowWidth = Constants.SCREEN_WIDTH;
    private final int windowHeight = Constants.SCREEN_HEIGHT;
    private BitmapFont customFont;
    private BitmapFont musicFont;
    private BitmapFont sfxFont;
    private GlyphLayout layout;
    private GlyphLayout musicLayout;
    private GlyphLayout sfxLayout;
    private boolean musicActive = false, sfxActive = false;
    String gameTitle, bgMusic, SFX;
    int buttonWidth = 130;
    int buttonHeight = 130;
    int spacing = 80;
    int startX = Constants.SCREEN_WIDTH / 2 - windowWidth / 2 + (windowWidth - (buttonWidth * 3 + spacing * 2)) / 2;
    int startY = Constants.SCREEN_HEIGHT / 2 - windowHeight / 2 + (windowHeight - buttonHeight) / 2 + 50;

    public PauseScreen(MyGdxGame myGdxGame, IngameScreen ingameScreen) {
        this.myGdxGame = myGdxGame;
        this.ingameScreen = ingameScreen;
        this.btnResume = new Button(startX, startY, buttonWidth, buttonHeight, Constants.RESUME_ICON_INACTIVE_PATH, Constants.RESUME_ICON_ACTIVE_PATH);
        this.btnRestart = new Button(startX + buttonWidth + spacing, startY, buttonWidth, buttonHeight, Constants.RESTART_ICON_INACTIVE_PATH, Constants.RESTART_ICON_ACTIVE_PATH);
        this.btnHome = new Button(startX + buttonWidth * 2 + spacing * 2, startY, buttonWidth, buttonHeight, Constants.HOME_ICON_INACTIVE_PATH, Constants.HOME_ICON_ACTIVE_PATH);
        this.btnMusic = new Button(startX + buttonWidth + spacing / 2 - 60, startY - 250, buttonWidth, buttonHeight, Constants.BGMUSIC_ICON_INACTIVE_PATH, Constants.BGMUSIC_ICON_ACTIVE_PATH);
        this.btnSFX = new Button(startX + buttonWidth * 2 + spacing + 20, startY - 250, buttonWidth, buttonHeight, Constants.SFX_ICON_INACTIVE_PATH, Constants.SFX_ICON_ACTIVE_PATH);
        this.pauseWindow = new Texture(Constants.PAUSE_BG_PATH);
        initFont();
        this.musicActive = GameManager.getInstance().isMusicActive();
        this.sfxActive = GameManager.getInstance().isSfxActive();
    }

    @Override
    public void show() {
        MyGdxGame.batch.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        boolean isGamePaused = ingameScreen.isPaused();
        ingameScreen.setPaused(true);

        ingameScreen.render(0);
        ingameScreen.setPaused(isGamePaused);

        MyGdxGame.batch.begin();
        MyGdxGame.batch.draw(pauseWindow, Constants.SCREEN_WIDTH / 2 - windowWidth / 2, Constants.SCREEN_HEIGHT / 2 - windowHeight / 2, windowWidth, windowHeight);

        gameTitle = "Mobs INC";
        layout.setText(customFont, gameTitle);
        float titleWidth = layout.width;
        float titleX = (Constants.SCREEN_WIDTH - titleWidth) / 2;
        customFont.draw(MyGdxGame.batch, gameTitle, titleX, Constants.SCREEN_HEIGHT / 2 + windowHeight / 2 - 150);

        btnResume.draw(MyGdxGame.batch);
        btnRestart.draw(MyGdxGame.batch);
        btnHome.draw(MyGdxGame.batch);

        bgMusic = "MUSIC";
        SFX = "SFX";
        float textYOffset = 60;
        MyGdxGame.batch.end();

        MyGdxGame.batch.begin();
        musicLayout.setText(musicFont, bgMusic);
        float musicWidth = musicLayout.width;
        float musicX = startX + buttonWidth + spacing / 2 - musicWidth / 2;
        musicFont.draw(MyGdxGame.batch, bgMusic, musicX, startY - textYOffset);

        sfxLayout.setText(sfxFont, SFX);
        float sfxWidth = sfxLayout.width;
        float sfxX = startX + buttonWidth * 2 + spacing * 2 - sfxWidth / 2;
        sfxFont.draw(MyGdxGame.batch, SFX, sfxX, startY - textYOffset);
        if (btnMusic.isClicked()) {
            musicActive = !musicActive;
            GameManager.getInstance().setMusicActive(musicActive);
            btnMusic.setClicked(false);
        }
        if (musicActive) {
            btnMusic.drawActive(MyGdxGame.batch);
        } else {
            btnMusic.drawInactive(MyGdxGame.batch);
        }

        // vẽ button sfx
        if (btnSFX.isClicked()) {
            sfxActive = !sfxActive;
            GameManager.getInstance().setSfxActive(sfxActive);
            btnSFX.setClicked(false);
        }
        if (sfxActive) {
            btnSFX.drawActive(MyGdxGame.batch);
        } else {
            btnSFX.drawInactive(MyGdxGame.batch);
        }
        MyGdxGame.batch.end();

        btnResume.update();
        btnRestart.update();
        btnHome.update();
        btnMusic.update();
        btnSFX.update();

        updateButtonResume();
        updateButtonHome();
        updateButtonRestart();
        updateButtonMusic();
        updateButtonSFX();
    }

    public void updateButtonResume(){
        if(btnResume.isClicked()){
            btnResume.setClicked(false);
            ingameScreen.setPaused(false);
            myGdxGame.setScreen(ingameScreen);
        }
    }

    public  void updateButtonHome(){
        if (btnHome.isClicked()) {
            btnHome.setClicked(false);
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            ConstantSound.getInstance().bgmIngame.dispose();
        }
    }
    public void updateButtonRestart() {
        if (btnRestart.isClicked()) {
            btnRestart.setClicked(false);
            this.dispose();
            ConstantSound.getInstance().bgmIngame.dispose();
            GameManager.getInstance().setCurrentPlayer(new Ghost());
            GameManager.getInstance().gameState = GameState.INGAME;
            // Clear enemies
            GameManager.getInstance().getEnemies().clear();
            GameManager.getInstance().resetSoundSettings();

            // Update SFX volume based on the reset settings
            if (GameManager.getInstance().isSfxActive()) {
                ConstantSound.getInstance().setSoundVolume(0f);
            } else {
                ConstantSound.getInstance().setSoundVolume(0.5f);
            }

            myGdxGame.setScreen(new IngameScreen(myGdxGame));
        }
    }

    public void updateButtonMusic(){
        if (btnMusic.isClicked()) {
            if (musicActive) {
                ConstantSound.getInstance().bgmIngame.play();
                musicActive = false;
            } else {
                ConstantSound.getInstance().bgmIngame.stop();
                musicActive = true;
            }
            GameManager.getInstance().setMusicActive(musicActive);
            btnMusic.setClicked(false);
        }
    }
    public void updateButtonSFX() {
        if (btnSFX.isClicked()) {
            if (sfxActive) {
                ConstantSound.getInstance().setSoundVolume(0.5f);
                sfxActive = false;
            } else {
                ConstantSound.getInstance().setSoundVolume(0f);
                sfxActive = true;
            }
            GameManager.getInstance().setSfxActive(sfxActive);
            btnSFX.setClicked(false);
        }
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PauseScreen/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 180;
        params.color = new Color(224 / 255f, 224 / 255f, 224 / 255f, 1);
        customFont = generator.generateFont(params);

        FreeTypeFontGenerator musicFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PauseScreen/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter musicParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        musicParams.size = 60;
        musicParams.color = new Color(224 / 255f, 224 / 255f, 224 / 255f, 1);
        musicFont = musicFontGenerator.generateFont(musicParams);

        FreeTypeFontGenerator sfxFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PauseScreen/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sfxParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sfxParams.size = 60;
        sfxParams.color = new Color(224 / 255f, 224 / 255f, 224 / 255f, 1);
        sfxFont = sfxFontGenerator.generateFont(sfxParams);

        generator.dispose();
        musicFontGenerator.dispose();
        sfxFontGenerator.dispose();

        layout = new GlyphLayout();
        musicLayout = new GlyphLayout();
        sfxLayout = new GlyphLayout();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        btnResume.dispose();
        btnRestart.dispose();
        btnHome.dispose();
        btnMusic.dispose();
        btnSFX.dispose();
        pauseWindow.dispose();
        customFont.dispose();
    }
}
