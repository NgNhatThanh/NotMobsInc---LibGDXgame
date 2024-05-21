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
import btck.com.ui.Button;
import btck.com.view.hud.HealthBar;
import btck.com.view.hud.LevelDisplay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Iterator;
import java.util.Random;

public class IngameScreen implements Screen {
    private MyGdxGame myGdxGame;
    private Camera cam;
    private Viewport viewport;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private int maxEnemyAmount = 10;
    private int maxEnemySpawnAtOnce = 3;
    private Player player;
    private Random rand;
    private long lastEnemySpawntime;
    private Spawner spawner;
    private Button btnQuit;
    private int quitX = Constants.screenWidth - 200, quitY = 50;
    private int quitWidth = 135;
    private int quitHeight = 50;
    private HealthBar healthBar;
    private LevelDisplay levelDisplay;
    private Texture map;

    public IngameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        this.rand = new Random();
        this.player = GameManager.getInstance().getCurrentPlayer();

        this.btnQuit = new Button(quitX, quitY, quitWidth, quitHeight, Constants.quitIconInactivePath, Constants.quitIconActivePath);
        this.spawner = new Spawner(maxEnemyAmount, maxEnemySpawnAtOnce);

        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(Constants.screenWidth, Constants.screenHeight, cam);
        Gdx.input.setInputProcessor(new MouseHandler());
        ConstantSound.bgmIngame.setVolume(ConstantSound.getBgmVolume());
        ConstantSound.bgmIngame.play();

        this.stage = new Stage(viewport, myGdxGame.batch);
        this.shapeRenderer = new ShapeRenderer();

        // Thêm Health Bar vào stage
        this.healthBar = new HealthBar(player);
        healthBar.setSize(200, 20);
        healthBar.setPosition(Constants.screenWidth - 220, Constants.screenHeight - 40);
        stage.addActor(healthBar);

        // thêm Level
        this.levelDisplay = new LevelDisplay(player);
        stage.addActor(levelDisplay);

        map = new Texture(Constants.mapPath);
    }

    int playerSpawnX = Constants.screenWidth / 2 - GameManager.getInstance().getCurrentPlayer().width / 2;
    int playerSpawnY = Constants.screenHeight / 2 - GameManager.getInstance().getCurrentPlayer().height / 2;

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

        myGdxGame.batch.setProjectionMatrix(cam.combined);

        myGdxGame.batch.begin();
        myGdxGame.batch.draw(map, 0, 0, Constants.screenWidth, Constants.screenHeight);

        updateBtnQuit();

        for (Iterator<Enemy> enemyIterator = GameManager.getInstance().getEnemies().iterator(); enemyIterator.hasNext(); ) {
            Enemy tmp = enemyIterator.next();

            tmp.draw(myGdxGame.batch);
            if(tmp.isVulnerable() && player.isAttacking() && player.getAttack().hit(tmp)){
                player.getAttack().addHitEntity(tmp);
            }

            if(player.isVulnerable() && tmp.isAttacking() && tmp.getAttack().hit(player)){
                tmp.getAttack().addHitEntity(player);
            }

            if(!tmp.isExist()){
                System.out.println("chet");
                enemyIterator.remove();
            }
        }

        GameManager.getInstance().getCurrentPlayer().draw(myGdxGame.batch);
        myGdxGame.batch.end();

        stage.act();
        stage.draw();

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
        ConstantSound.bgmIngame.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        levelDisplay.dispose();
        healthBar.dispose();
        map.dispose();
    }

    public void updateBtnQuit(){
        btnQuit.update();
        btnQuit.draw(myGdxGame.batch);
        if(btnQuit.isClicked()){
            btnQuit.setClicked(false);
            this.dispose();
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
        }
    }
}
