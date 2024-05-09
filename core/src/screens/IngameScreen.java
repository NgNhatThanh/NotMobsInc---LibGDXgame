package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.io.sound.ConstantSound;
import btck.com.controller.spawn.Spawner;
import btck.com.model.constant.GameConstant;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
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
    
    Array<Enemy> enemies;

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

        spawner = new Spawner(this, maxEnemyAmount, maxEnemySpawnAtOnce);
        this.myGdxGame = myGdxGame;
        cam = new OrthographicCamera();
        viewport = new FitViewport(GameConstant.screenWidth, GameConstant.screenHeight, cam);
        Gdx.input.setInputProcessor(new MouseHandler());
        ConstantSound.bgmIngame.play();
        hud = new HUD(myGdxGame.batch);


        quitActive = new Texture("ingame/quit active-02.png");
        quitInactive = new Texture("ingame/quit-02.png");


//        cam.position.set(GameConstant.screenWidth / 2, GameConstant.screenHeight / 2, 0);
        enemies = new Array<>();
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

        if(System.currentTimeMillis() - lastEnemySpawntime >= 5000){
            lastEnemySpawntime = System.currentTimeMillis();
            spawner.spawnEnemy();
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        myGdxGame.batch.setProjectionMatrix(cam.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(map, 0, 0, GameConstant.screenWidth, GameConstant.screenHeight);

        drawQuitLabel();
//        System.out.println(enemies.size);

        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
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

        if(!GameManager.getInstance().getCurrentPlayer().isExist()){
            System.out.println("chet");
            this.dispose();
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
        }
    }

    public void spawnPlayer(){
        GameManager.getInstance().getCurrentPlayer().setX(playerSpawnX);
        GameManager.getInstance().getCurrentPlayer().setY(playerSpawnY);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public int getCurrentEnemyAmount(){
        return enemies.size;
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
        hud.dispose();
        map.dispose();
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
