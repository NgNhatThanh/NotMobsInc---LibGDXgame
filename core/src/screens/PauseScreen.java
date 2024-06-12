package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.Constants;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.GameState;
import btck.com.model.entity.player.ghost.Ghost;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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

        int buttonWidth = 80;
        int buttonHeight = 60;
        int buttonY = Constants.SCREEN_HEIGHT / 2 - windowHeight / 2 + 100;
        int spacing = 20; // Khoảng cách giữa các button

        int totalWidth = buttonWidth * 3 + spacing * 2;
        int startX = Constants.SCREEN_WIDTH / 2 - totalWidth / 2;

        // Tạo và đặt vị trí cho các button
        this.btnResume = new Button(startX, buttonY, buttonWidth, buttonHeight, Constants.RESUME_ICON_INACTIVE_PATH, Constants.RESUME_ICON_ACTIVE_PATH);
        this.btnRestart = new Button(startX + buttonWidth + spacing, buttonY, buttonWidth, buttonHeight, Constants.RESTART_ICON_INACTIVE_PATH, Constants.RESTART_ICON_ACTIVE_PATH);
        this.btnQuit = new Button(startX + buttonWidth * 2 + spacing * 2, buttonY, buttonWidth, buttonHeight, Constants.QUIT2_ICON_INACTIVE_PATH, Constants.QUIT2_ICON_ACTIVE_PATH);
        this.pauseWindow = new Texture(Constants.PAUSE_BG_PATH);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Render phần nền của màn hình ingame
        ingameScreen.render(0); // Đặt delta = 0 để đảm bảo trạng thái không thay đổi

        // Vẽ nền mờ
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        MyGdxGame.batch.begin();
        MyGdxGame.batch.setColor(1, 1, 1, 0.5f);
        MyGdxGame.batch.draw(pauseWindow, Constants.SCREEN_WIDTH / 2 - windowWidth / 2, Constants.SCREEN_HEIGHT / 2 - windowHeight / 2, windowWidth, windowHeight);
        MyGdxGame.batch.setColor(1, 1, 1, 1);
        MyGdxGame.batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Vẽ các nút trong cửa sổ pause
        MyGdxGame.batch.begin();
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
