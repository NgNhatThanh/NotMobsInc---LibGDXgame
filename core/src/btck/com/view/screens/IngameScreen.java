package btck.com.view.screens;

import btck.com.common.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.MouseHandler;
import btck.com.common.sound.ConstantSound;
import btck.com.controller.spawn.Spawner;
import btck.com.common.Constants;
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
    private final MyGdxGame myGdxGame;
    private final OrthographicCamera cam;
    private final Viewport viewport;
    private final int maxEnemyAmount = 10;
    private final int maxEnemySpawnAtOnce = 3;
    private final Player player;
    private final Random rand;
    private long lastEnemySpawntime;
    private final Spawner spawner;
    private final Button btnQuit;
    private final int quitHeight = 50;
    private final int quitWidth = 135;
    private final int quitX = Constants.SCREEN_WIDTH - quitWidth - 60;
    private final int quitY = Constants.SCREEN_HEIGHT - quitHeight - 30;
    private final Texture map;
    private final Texture frame;
    private final HUD hud;
    private static Array<Effect> topLayerEffects;
    private static Array<Effect> bottomLayerEffects;

    Vector3 center = new Vector3((float) Constants.SCREEN_WIDTH / 2, (float) Constants.SCREEN_HEIGHT / 2, 0);

    public IngameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        this.rand = new Random();
        this.player = GameManager.getInstance().getCurrentPlayer();
        hud = new HUD();

        topLayerEffects = new Array<>();
        bottomLayerEffects = new Array<>();
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

        MyGdxGame.batch.setProjectionMatrix(cam.combined);

        MyGdxGame.batch.begin();
        MyGdxGame.batch.draw(map, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        for(Iterator<Effect> eff = bottomLayerEffects.iterator(); eff.hasNext(); ){
            Effect tmp = eff.next();
            tmp.draw();
            if(tmp.isFinished()) eff.remove();
        }

        for (Iterator<Enemy> enemyIterator = GameManager.getInstance().getEnemies().iterator(); enemyIterator.hasNext(); ) {
            Enemy tmp = enemyIterator.next();

            tmp.draw(MyGdxGame.batch);
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

        GameManager.getInstance().getCurrentPlayer().draw(MyGdxGame.batch);

        for(Iterator<Effect> eff = topLayerEffects.iterator(); eff.hasNext(); ){
            Effect tmp = eff.next();
            tmp.draw();
            if(tmp.isFinished()) eff.remove();
        }

        if(Rumble.isRumbling() && cam.position.equals(center)) cam.translate(Rumble.tick(Gdx.graphics.getDeltaTime()));
        else cam.position.set(center);
        cam.update();

        MyGdxGame.batch.draw(frame, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        updateBtnQuit();

        MyGdxGame.batch.end();

        hud.draw();

        if(Debugger.debugMode == DEBUG_MODE.ON) {
            Debugger.getInstance();
            Debugger.debug();
        }

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
        btnQuit.draw(MyGdxGame.batch);
        if(btnQuit.isClicked()){
            btnQuit.setClicked(false);
            this.dispose();
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
        }
    }
}
