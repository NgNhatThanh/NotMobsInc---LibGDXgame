package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.Constants;
import btck.com.model.constant.GameState;
import btck.com.model.entity.player.swordman.Swordman;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sun.tools.jconsole.JConsoleContext;

public class MainMenuScreen  implements Screen {
    public static final int newGameWidth = 500;
    public static final int newGameHeight = 120;
    public static final int exitWidth = 230;
    public static final int exitHeight = 92;
    public static final int creditWidth = 500;
    public static final int creditHeight = 100;
    public static final int settingWidth = 400;
    public static final int settingHeight = 100;
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    MyGdxGame myGdxGame;

    Texture newGameActive;
    Texture newGameInactive;
    Texture exitInactive;
    Texture exitActive;
    Texture creditActive;
    Texture creditInactive;
    Texture settingActive;
    Texture settingInactive;
    public MainMenuScreen(MyGdxGame myGdxGame) {
        Gdx.input.setInputProcessor(new InputAdapter());
        System.out.println(MainMenuScreen.WIDTH);
        System.out.println(MainMenuScreen.HEIGHT);
        this.myGdxGame = myGdxGame;
        newGameActive = new Texture(Constants.newGameIconActivePath);
        newGameInactive = new Texture(Constants.newGameIconInactivePath);
        exitInactive = new Texture(Constants.exitIconInactivePath);
        exitActive = new Texture(Constants.exitIconActivePath);
        creditActive = new Texture(Constants.creditIconActivePath);
        creditInactive = new Texture(Constants.creditIconInactivePath);
        settingActive = new Texture(Constants.settingIconActivePath);
        settingInactive = new Texture(Constants.settingIconInactivePath);
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

        int newGameX = (WIDTH - newGameWidth - 20);
        int newGameY = (HEIGHT - newGameHeight - 50);
        int creditX = newGameX - 15;
        int creditY = newGameY - 120;
        int settingX = newGameX;
        int settingY = creditY - 120;
        int exitX = newGameX + 5;
        int exitY = settingY - 120;


        if(Gdx.input.getX() < newGameX + newGameWidth && Gdx.input.getX() > newGameX && MainMenuScreen.HEIGHT - Gdx.input.getY() < newGameY + newGameHeight && MainMenuScreen.HEIGHT - Gdx.input.getY() > newGameY){
            myGdxGame.batch.draw(newGameActive, newGameX, newGameY, newGameWidth, newGameHeight);
            if(Gdx.input.isTouched()){
                ConstantSound.bgm.dispose();
                this.dispose();
                GameManager.getInstance().setCurrentPlayer(new Swordman());
                GameManager.getInstance().gameState = GameState.INGAME;
                myGdxGame.setScreen(new IngameScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(newGameInactive, newGameX, newGameY, newGameWidth, newGameHeight);
        }

        if(Gdx.input.getX() < exitX + exitWidth && Gdx.input.getX() > exitX && MainMenuScreen.HEIGHT - Gdx.input.getY() < exitY + newGameHeight && MainMenuScreen.HEIGHT - Gdx.input.getY() > exitY){
            myGdxGame.batch.draw(exitActive, exitX, exitY, exitWidth, exitHeight);
            if(Gdx.input.isTouched()){
                ConstantSound.dispose();
                Gdx.app.exit();
            }
        }else{
            myGdxGame.batch.draw(exitInactive, exitX, exitY, exitWidth, exitHeight);
        }

        if(Gdx.input.getX() < creditX + creditWidth && Gdx.input.getX() > creditX && MainMenuScreen.HEIGHT - Gdx.input.getY() < creditY + creditHeight && MainMenuScreen.HEIGHT - Gdx.input.getY() > creditY){
            myGdxGame.batch.draw(creditActive, creditX, creditY, creditWidth, creditHeight);
            if(Gdx.input.isTouched()){
                myGdxGame.setScreen(new CreditsScene(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(creditInactive, creditX, creditY, creditWidth, creditHeight);
        }

        if(Gdx.input.getX() < settingX + settingWidth && Gdx.input.getX() > settingX && MainMenuScreen.HEIGHT - Gdx.input.getY() < settingY + settingHeight && MainMenuScreen.HEIGHT - Gdx.input.getY() > settingY){
            myGdxGame.batch.draw(settingActive, settingX, settingY, settingWidth, settingHeight);
            if(Gdx.input.isTouched()){
                myGdxGame.setScreen(new SettingScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(settingInactive, settingX, settingY, settingWidth, settingHeight);
        }

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
        this.newGameActive.dispose();
        this.newGameInactive.dispose();
        this.exitActive.dispose();
        this.exitInactive.dispose();
        this.creditActive.dispose();
        this.creditInactive.dispose();
    }
}

