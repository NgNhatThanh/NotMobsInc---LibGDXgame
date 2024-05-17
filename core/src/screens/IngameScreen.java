package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.io.sound.ConstantSound;
import btck.com.controller.spawn.Spawner;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.utils.DEBUG_MODE;
import btck.com.utils.Debugger;
import btck.com.view.hud.HUD;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.Random;


public class IngameScreen implements Screen {

    MyGdxGame myGdxGame;

    private Camera cam;

    private Viewport viewport;

    HUD hud;

    int maxEnemyAmount = 7;
    int maxEnemySpawnAtOnce = 3;

    Player player;

    Random rand;

    long lastEnemySpawntime;

    Spawner spawner;

    Texture quitInactive;
    Texture quitActive;

    public IngameScreen(MyGdxGame myGdxGame){
        rand = new Random();
        player = GameManager.getInstance().getCurrentPlayer();

        spawner = new Spawner( maxEnemyAmount, maxEnemySpawnAtOnce);
        this.myGdxGame = myGdxGame;
        cam = new OrthographicCamera();
        viewport = new FitViewport(Constants.screenWidth, Constants.screenHeight, cam);
        Gdx.input.setInputProcessor(new MouseHandler());
        ConstantSound.bgmIngame.play();
        hud = new HUD(myGdxGame.batch);


        quitActive = new Texture(Constants.quitIconActivePath);
        quitInactive = new Texture(Constants.quitIconInactivePath);
        map = new Texture(Constants.mapPath);


//        cam.position.set(GameConstant.screenWidth / 2, GameConstant.screenHeight / 2, 0);
        //        viewport.apply(true);
//        cam.position.set(0, 0, 0);
//        cam.update();
//        viewport.setScreenX(0);
//        viewport.setScreenY(0);
    }

    Texture map;

    int playerSpawnX = Constants.screenWidth / 2 - GameManager.getInstance().getCurrentPlayer().width / 2;
    int playerSpawnY = Constants.screenHeight / 2 - GameManager.getInstance().getCurrentPlayer().height / 2;;

    @Override
    public void show() {
        spawnPlayer();
    }

    @Override
    public void render(float delta) {

        if(System.currentTimeMillis() - lastEnemySpawntime >= 5000){
            lastEnemySpawntime = System.currentTimeMillis();
            spawner.spawnEnemy();
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        myGdxGame.batch.setProjectionMatrix(cam.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(map, 0, 0, Constants.screenWidth, Constants.screenHeight);

        drawQuitLabel();
//        System.out.println(enemies.size);

        for (Iterator<Enemy> enemyIterator = GameManager.getInstance().getEnemies().iterator(); enemyIterator.hasNext(); ) {
            Enemy tmp = enemyIterator.next();

            tmp.draw(myGdxGame.batch);
            if(player.isAttacking() && player.getAttack().hit(tmp)){
                player.getAttack().addHitEntity(tmp);
            }

            if(tmp.isAttacking() && tmp.getAttack().hit(player)){
                tmp.getAttack().addHitEntity(player);
            }

            if(!tmp.isExist()){
                System.out.println("chet");
                enemyIterator.remove();
            }
        }

        GameManager.getInstance().getCurrentPlayer().draw(myGdxGame.batch);

        myGdxGame.batch.end();

//        myGdxGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update();
        hud.stage.draw();

        if(Debugger.debugMode == DEBUG_MODE.ON) Debugger.getInstance().debug();

        if(!GameManager.getInstance().getCurrentPlayer().isExist()){
            System.out.println("chet");
            this.dispose();
            myGdxGame.setScreen(new GameOverScreen(myGdxGame));
        }
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

        ConstantSound.bgmIngame.dispose();
        ConstantSound.slash.dispose();
        hud.dispose();
        map.dispose();
    }

    public void drawQuitLabel(){
        int labelX = Constants.screenWidth - 200, labelY = 50;
        int quitWidth = 135;
        int quitHeight = 50;
        if(Gdx.input.getX() < labelX + quitWidth && Gdx.input.getX() > labelX && Constants.screenHeight - Gdx.input.getY() < labelY + quitHeight && Constants.screenHeight - Gdx.input.getY() > labelY){
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
