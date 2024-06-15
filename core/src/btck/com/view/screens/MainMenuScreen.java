package btck.com.view.screens;

import btck.com.common.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.sound.ConstantSound;
import btck.com.common.Constants;
import btck.com.model.state.GameState;
import btck.com.model.entity.player.ghost.Ghost;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static btck.com.common.io.Constants.inited;
//import com.sun.tools.jconsole.JConsoleContext;

public class MainMenuScreen  implements Screen {
    float FRAME_SPEED = 0.08f;
    int width, height;
    protected TextureAtlas textureAtlas;
    protected Animation<TextureRegion>[] animations;
    protected int animationIdx;
    protected float statetime;
    static Sound sfx = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/door.ogg"));
    public final int newGameWidth = 400;
    public final int newGameHeight = 96;
    public final int exitWidth = 150;
    public final int exitHeight = 60;
    public final int creditWidth = 400;
    public final int creditHeight = 80;
    public final int settingWidth = 300;
    public final int settingHeight = 75;
    int newGameX = (Constants.SCREEN_WIDTH - newGameWidth - 40);
    int newGameY = (Constants.SCREEN_HEIGHT - newGameHeight - 50);
    int creditX = newGameX - 15;
    int creditY = newGameY - 85;
    int settingX = newGameX;
    int settingY = creditY - 85;
    int exitX = newGameX + 5;
    int exitY = settingY - 80;
    MyGdxGame myGdxGame;
    Button btnNewGame;
    Button btnExit;
    Button btnSetting;
    Button btnCredit;
    private String[] menuItems = new String[]{"New game", "Settings", "Credits", "Exit"};
    private Button[] menuButtons = new Button[menuItems.length];
    public MainMenuScreen(MyGdxGame myGdxGame) {
        Gdx.input.setInputProcessor(new InputAdapter());
        MyGdxGame.batch.setColor(Color.WHITE);
        this.myGdxGame = myGdxGame;

        width = Constants.SCREEN_WIDTH;
        height = Constants.SCREEN_HEIGHT;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.MAIN_MENU_SCREEN_ATLAS_PATH));
        animations = new Animation[3];
        if(Constants.inited == true) animationIdx = 1;
        else animationIdx = 0;

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("tile"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("loop"));
        animations[2] = new Animation<>(0.1f, textureAtlas.findRegions("close"));

        btnNewGame = new Button(newGameX, newGameY, newGameWidth, newGameHeight, Constants.NEW_GAME_ICON_INACTIVE_PATH, Constants.NEW_GAME_ICON_ACTIVE_PATH);
        btnExit = new Button(exitX, exitY, exitWidth, exitHeight, Constants.EXIT_ICON_INACTIVE_PATH, Constants.EXIT_ICON_ACTIVE_PATH);
        btnSetting = new Button(settingX, settingY, settingWidth, settingHeight, Constants.SETTING_ICON_INACTIVE_PATH, Constants.SETTING_ICON_ACTIVE_PATH);
        btnCredit = new Button(creditX, creditY, creditWidth, creditHeight, Constants.CREDIT_ICON_INACTIVE_PATH, Constants.CREDIT_ICON_ACTIVE_PATH);

        menuButtons[0] = btnNewGame;
        menuButtons[1] = btnSetting;
        menuButtons[2] = btnCredit;
        menuButtons[3] = btnExit;
        for(int i = 0; i < menuItems.length; ++i){
            menuButtons[i].setText(menuItems[i]);
        }

        ConstantSound.getInstance().bgmMenu.setVolume(ConstantSound.getInstance().getBgmVolume());
        ConstantSound.getInstance().bgmMenu.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        statetime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (animationIdx < animations.length - 2 && animations[animationIdx].isAnimationFinished(statetime)) {
            animationIdx++;
        }

        if(animations[0].isAnimationFinished(statetime)) Constants.inited = true;
        if(animationIdx == 2 && animations[2].isAnimationFinished(statetime)){
            ConstantSound.getInstance().bgmMenu.dispose();
            this.dispose();
            GameManager.getInstance().setCurrentPlayer(new Ghost());
            GameManager.getInstance().gameState = GameState.INGAME;
            // Clear enemies
            GameManager.getInstance().getEnemies().clear();
            myGdxGame.setScreen(new IngameScreen(myGdxGame));
        }

        myGdxGame.batch.begin();

        if(animationIdx == 0 || animationIdx == 2){
            myGdxGame.batch.draw(animations[animationIdx].getKeyFrame(statetime, false), 0, 0, width, height);
        }
        else myGdxGame.batch.draw(animations[animationIdx].getKeyFrame(statetime, true), 0, 0, width, height);
        if(animationIdx == 1) update();

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
                        animationIdx = 2;
                        sfx.play(ConstantSound.getInstance().getSoundVolume());
                        statetime = 0f;
                        break;
                    case "Settings":
                        myGdxGame.setScreen(new SettingScreen(myGdxGame));
                        break;
                    case "Credits":
                        myGdxGame.setScreen(new CreditsScene(myGdxGame));
                        break;
                    case "Exit":
                        ConstantSound.getInstance().dispose();
                        Gdx.app.exit();
                        break;
                }
            }
        }
    }
}

