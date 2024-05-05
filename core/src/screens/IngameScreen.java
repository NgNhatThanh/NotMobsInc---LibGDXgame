package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.model.constant.ConstantSound;
import btck.com.model.constant.GameConstant;
import btck.com.view.hud.HUD;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class IngameScreen implements Screen {

    MyGdxGame myGdxGame;

    private Camera cam;

    private Viewport viewport;

    HUD hud;

    Texture quitInactive;
    Texture quitActive;

    public IngameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        cam = new OrthographicCamera();
        viewport = new FitViewport(GameConstant.screenWidth, GameConstant.screenHeight, cam);
        Gdx.input.setInputProcessor(new MouseHandler());
        ConstantSound.bgmIngame.play();
        hud = new HUD(myGdxGame.batch);

        quitActive = new Texture("ingame/quit active-02.png");
        quitInactive = new Texture("ingame/quit-02.png");

        //        viewport.apply(true);
//        cam.position.set(0, 0, 0);
//        cam.update();
//        viewport.setScreenX(0);
//        viewport.setScreenY(0);
    }

    Texture map;

    int playerSpawnX = GameConstant.screenWidth / 2 - GameManager.getInstance().getCurrentPlayer().width / 2;
    int playerSpawnY = GameConstant.screenHeight / 2 - GameManager.getInstance().getCurrentPlayer().height / 2;;

    @Override
    public void show() {
        map = new Texture("ingame/map.gif");

        spawnPlayer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(map, 0, 0, GameConstant.screenWidth, GameConstant.screenHeight);
        drawQuitLabel();

        GameManager.getInstance().getCurrentPlayer().draw(myGdxGame.batch);
        myGdxGame.batch.end();

//        myGdxGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void spawnPlayer(){
        GameManager.getInstance().getCurrentPlayer().setX(playerSpawnX);
        GameManager.getInstance().getCurrentPlayer().setY(playerSpawnY);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        quitInactive.dispose();
        quitActive.dispose();

    }

    public void drawQuitLabel(){
        int labelX = GameConstant.screenWidth - 200, labelY = 50;
        int quitWidth = 135;
        int quitHeight = 50;
        if(Gdx.input.getX() < labelX + quitWidth && Gdx.input.getX() > labelX && GameConstant.screenHeight - Gdx.input.getY() < labelY + quitHeight && GameConstant.screenHeight - Gdx.input.getY() > labelY){
            myGdxGame.batch.draw(quitActive, labelX, labelY, quitWidth, quitHeight);
            if(Gdx.input.isTouched()){
                ConstantSound.bgmIngame.dispose();
                this.dispose();
                myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(quitInactive, labelX, labelY, quitWidth, quitHeight);
        }
    }
}

