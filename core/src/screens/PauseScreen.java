package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.Constants;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.GameState;
import btck.com.model.entity.player.ghost.Ghost;
import btck.com.ui.Button;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class PauseScreen implements Screen {
    private final MyGdxGame myGdxGame;
    private final IngameScreen ingameScreen;
    private final Button btnResume;
    private final Button btnQuit;
    private final Button btnRestart;
    private final Texture pauseWindow;
    private final int windowWidth = 500;
    private final int windowHeight = 300;

    public PauseScreen(MyGdxGame myGdxGame, IngameScreen ingameScreen) {
        this.myGdxGame = myGdxGame;
        this.ingameScreen = ingameScreen;

        int buttonWidth = 75;
        int buttonHeight = 75;
        int spacing = 40;


        int startX = Constants.SCREEN_WIDTH / 2 - windowWidth / 2 + (windowWidth - (buttonWidth * 3 + spacing * 2)) / 2;
        int startY = Constants.SCREEN_HEIGHT / 2 - windowHeight / 2 + (windowHeight - buttonHeight) / 2;

        this.btnResume = new Button(startX, startY, buttonWidth, buttonHeight, Constants.RESUME_ICON_INACTIVE_PATH, Constants.RESUME_ICON_ACTIVE_PATH);
        this.btnRestart = new Button(startX + buttonWidth + spacing, startY, buttonWidth, buttonHeight, Constants.RESTART_ICON_INACTIVE_PATH, Constants.RESTART_ICON_ACTIVE_PATH);
        this.btnQuit = new Button(startX + buttonWidth * 2 + spacing * 2, startY, buttonWidth, buttonHeight, Constants.QUIT2_ICON_INACTIVE_PATH, Constants.QUIT2_ICON_ACTIVE_PATH);
        this.pauseWindow = new Texture(Constants.PAUSE_BG_PATH);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        boolean isGamePaused = ingameScreen.isPaused();
        ingameScreen.setPaused(true);

        ingameScreen.render(0);
        ingameScreen.setPaused(isGamePaused);

        MyGdxGame.batch.begin();
        MyGdxGame.batch.draw(pauseWindow, Constants.SCREEN_WIDTH / 2 - windowWidth / 2, Constants.SCREEN_HEIGHT / 2 - windowHeight / 2, windowWidth, windowHeight);
        btnResume.draw(MyGdxGame.batch);
        btnRestart.draw(MyGdxGame.batch);
        btnQuit.draw(MyGdxGame.batch);
        MyGdxGame.batch.end();

        btnResume.update();
        btnRestart.update();
        btnQuit.update();

        if (btnResume.isClicked()) {
            btnResume.setClicked(false);
            ingameScreen.setPaused(false);
            myGdxGame.setScreen(ingameScreen);
        }

        if (btnQuit.isClicked()) {
            btnQuit.setClicked(false);
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            ConstantSound.getInstance().bgmIngame.dispose();
        }

        if (btnRestart.isClicked()) {
            btnRestart.setClicked(false);
            this.dispose();
            ConstantSound.getInstance().bgmIngame.dispose();
            GameManager.getInstance().setCurrentPlayer(new Ghost());
            GameManager.getInstance().gameState = GameState.INGAME;
            // Clear enemies
            GameManager.getInstance().getEnemies().clear();
            myGdxGame.setScreen(new IngameScreen(myGdxGame));
        }
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
        btnQuit.dispose();
        pauseWindow.dispose();
    }
}
