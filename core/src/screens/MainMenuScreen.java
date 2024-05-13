package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.Constants;
import btck.com.model.constant.GameState;
import btck.com.model.entity.player.swordman.Swordman;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.sun.tools.jconsole.JConsoleContext;

public class MainMenuScreen  implements Screen {
    public final int newGameWidth = 500;
    public final int newGameHeight = 120;
    public final int exitWidth = 230;
    public final int exitHeight = 92;
    public final int creditWidth = 500;
    public final int creditHeight = 100;
    public final int settingWidth = 400;
    public final int settingHeight = 100;
    int newGameX = (Constants.screenWidth - newGameWidth - 20);
    int newGameY = (Constants.screenHeight - newGameHeight - 50);
    int creditX = newGameX - 15;
    int creditY = newGameY - 120;
    int settingX = newGameX;
    int settingY = creditY - 120;
    int exitX = newGameX + 5;
    int exitY = settingY - 120;
    MyGdxGame myGdxGame;
    Button btnNewGame;
    Button btnExit;
    Button btnSetting;
    Button btnCredit;
    private String[] menuItems = new String[]{"New game", "Settings", "Credits", "Exit"};
    private Button[] menuButtons = new Button[menuItems.length];
    public MainMenuScreen(MyGdxGame myGdxGame) {
        Gdx.input.setInputProcessor(new InputAdapter());
        this.myGdxGame = myGdxGame;

        btnNewGame = new Button(newGameX, newGameY, newGameWidth, newGameHeight, Constants.newGameIconInactivePath, Constants.newGameIconActivePath);
        btnExit = new Button(exitX, exitY, exitWidth, exitHeight, Constants.exitIconInactivePath, Constants.exitIconActivePath);
        btnSetting = new Button(settingX, settingY, settingWidth, settingHeight, Constants.settingIconInactivePath, Constants.settingIconActivePath);
        btnCredit = new Button(creditX, creditY, creditWidth, creditHeight, Constants.creditIconInactivePath, Constants.creditIconActivePath);

        menuButtons[0] = btnNewGame;
        menuButtons[1] = btnSetting;
        menuButtons[2] = btnCredit;
        menuButtons[3] = btnExit;
        for(int i = 0; i < menuItems.length; ++i){
            menuButtons[i].setText(menuItems[i]);
        }

        ConstantSound.bgm.setVolume(ConstantSound.getBgmVolume());
        ConstantSound.bgm.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGdxGame.batch.begin();

        update();

        myGdxGame.batch.end();
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
    }

    public void update() {
        for (Button menuButton : menuButtons) {
            menuButton.update();
            menuButton.draw(myGdxGame.batch);
            if (menuButton.isClicked()) {
                menuButton.setClicked(false);
                switch (menuButton.getText()) {
                    case "New game":
                        ConstantSound.bgm.dispose();
                        this.dispose();
                        GameManager.getInstance().setCurrentPlayer(new Swordman());
                        GameManager.getInstance().gameState = GameState.INGAME;
                        myGdxGame.setScreen(new IngameScreen(myGdxGame));
                        break;
                    case "Settings":
                        myGdxGame.setScreen(new SettingScreen(myGdxGame));
                        break;
                    case "Credits":
                        myGdxGame.setScreen(new CreditsScene(myGdxGame));
                        break;
                    case "Exit":
                        ConstantSound.dispose();
                        Gdx.app.exit();
                        break;
                }
            }
        }
    }
}

