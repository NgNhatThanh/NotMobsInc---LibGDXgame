package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.model.constant.GameConstant;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.model.entity.enemy.Gladiator;
import btck.com.model.entity.enemy.Mushroom;
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

    Player player;

    Random rand;

    long lastEnemySpawntime;

    public IngameScreen(MyGdxGame myGdxGame){
        rand = new Random();
        player = GameManager.getInstance().getCurrentPlayer();

        this.myGdxGame = myGdxGame;
        cam = new OrthographicCamera();
        viewport = new FitViewport(GameConstant.screenWidth, GameConstant.screenHeight, cam);
        hud = new HUD(myGdxGame.batch);

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
            spawnEnemy();
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(map, 0, 0, GameConstant.screenWidth, GameConstant.screenHeight);
        System.out.println(enemies.size);
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
            Enemy tmp = enemyIterator.next();

            tmp.draw(myGdxGame.batch);
            if(player.isAttacking() && player.hit(tmp)){
                player.currentExp += tmp.exp;
                tmp.setHealth(tmp.getHealth() - player.damage);
            }
            if(!tmp.isExist()){
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

    public void spawnEnemy(){
        Enemy enemy = new Mushroom();

//        enemy.setX(rand.nextFloat(GameConstant.screenWidth - enemy.width));
//        enemy.setY(rand.nextFloat(GameConstant.screenHeight - enemy.height));
        float randomX = rand.nextInt((int) (GameConstant.screenWidth - enemy.width));
        float randomY = rand.nextInt((int) (GameConstant.screenHeight - enemy.height));
        enemy.setX(randomX);
        enemy.setY(randomY);
        enemies.add(enemy);
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
        hud.dispose();
        map.dispose();
    }
}
