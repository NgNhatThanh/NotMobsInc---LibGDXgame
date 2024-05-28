package screens;

import btck.com.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.io.sound.ConstantSound;
import btck.com.controller.spawn.Spawner;
import btck.com.common.io.Constants;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.utils.DEBUG_MODE;
import btck.com.utils.Debugger;
import btck.com.ui.Button;
import btck.com.view.effect.Effect;
import btck.com.view.hud.HUD;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Iterator;
import java.util.Random;

public class IngameScreen implements Screen {
    private MyGdxGame myGdxGame;
    private OrthographicCamera cam;
    private Viewport viewport;
    private int maxEnemyAmount = 10;
    private int maxEnemySpawnAtOnce = 3;
    private Player player;
    private Random rand;
    private long lastEnemySpawntime;
    private Spawner spawner;
    private Button btnQuit;
    private int quitHeight = 50;
    private int quitWidth = 135;
    private int quitX = Constants.SCREEN_WIDTH - quitWidth - 60;
    private int quitY = Constants.SCREEN_HEIGHT - quitHeight - 30;
    private Texture map;
    private Texture frame;
    private HUD hud;
    private static Array<Effect> topLayerEffects;
    private static Array<Effect> bottomLayerEffects;

    Vector3 center = new Vector3(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, 0);

    public IngameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        this.rand = new Random();
        this.player = GameManager.getInstance().getCurrentPlayer();
        hud = new HUD();

        this.topLayerEffects = new Array<>();
        this.bottomLayerEffects = new Array<>();
        this.btnQuit = new Button(quitX, quitY, quitWidth, quitHeight, Constants.QUIT_ICON_INACTIVE_PATH, Constants.QUIT_ICON_ACTIVE_PATH);
        this.spawner = new Spawner(maxEnemyAmount, maxEnemySpawnAtOnce);

        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, cam);

        Gdx.input.setInputProcessor(new MouseHandler());
        ConstantSound.getInstance().bgmIngame.setVolume(ConstantSound.getInstance().getBgmVolume());
        ConstantSound.getInstance().bgmIngame.play();

        frame = new Texture(Constants.FRAME_0_PATH);
        map = new Texture(Constants.MAP_PATH);
    }

    int playerSpawnX = Constants.SCREEN_WIDTH / 2 - GameManager.getInstance().getCurrentPlayer().width / 2;
    int playerSpawnY = Constants.SCREEN_HEIGHT / 2 - GameManager.getInstance().getCurrentPlayer().height / 2;

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
        myGdxGame.batch.draw(map, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        for(Iterator<Effect> eff = bottomLayerEffects.iterator(); eff.hasNext(); ){
            Effect tmp = eff.next();
            tmp.draw();
            if(tmp.isFinished()) eff.remove();
        }

        updateBtnQuit();

        for (Iterator<Enemy> enemyIterator = GameManager.getInstance().getEnemies().iterator(); enemyIterator.hasNext(); ) {
            Enemy tmp = enemyIterator.next();

            tmp.draw(myGdxGame.batch);
            if(tmp.isVulnerable() && player.isAttacking() && player.getAttack().hit(tmp)){
                Rumble.rumble();
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

        for(Iterator<Effect> eff = topLayerEffects.iterator(); eff.hasNext(); ){
            Effect tmp = eff.next();
            tmp.draw();
            if(tmp.isFinished()) eff.remove();
        }

        if(Rumble.isRumbling() && cam.position.equals(center)) cam.translate(Rumble.tick(Gdx.graphics.getDeltaTime()));
        else cam.position.set(center);
        cam.update();

        myGdxGame.batch.draw(frame, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        myGdxGame.batch.end();

        hud.draw();

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

    public static void addTopEffect(Effect eff){ topLayerEffects.add(eff); }

    public static void addBottomEffect(Effect eff){ bottomLayerEffects.add(eff); }

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
        ConstantSound.getInstance().bgmIngame.dispose();
        hud.dispose();
        map.dispose();
        frame.dispose();
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
