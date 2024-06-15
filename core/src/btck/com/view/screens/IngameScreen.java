package btck.com.view.screens;

import btck.com.common.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.io.IngameInputHandler;
import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.Bullet;
import btck.com.controller.spawn.Spawner;
import btck.com.common.Constants;
import btck.com.crowd_control.SlowMo;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import btck.com.utils.DEBUG_MODE;
import btck.com.utils.Debugger;
import btck.com.ui.Button;
import btck.com.view.effect.Effect;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import btck.com.view.hud.HUD;
import btck.com.view.screens.eff.Rumble;
import btck.com.view.screens.eff.ShockWave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class IngameScreen implements Screen {
    private final MyGdxGame myGdxGame;
    private final OrthographicCamera cam;
    private final Viewport viewport;
    private int maxEnemyAmount = 10;
    private int maxEnemySpawnAtOnce = 3;
    private final Player player;
    private float timeToSpawnEnemies;
    private float spawnTimeShorten;
    private long lastEnemySpawntime;
    private final Spawner spawner;
    private final Button btnPause;
    private final int pauseHeight = 80;
    private final int pauseWidth = 80;
    private final int pauseX = Constants.SCREEN_WIDTH - pauseWidth - 60;
    private final int pauseY = Constants.SCREEN_HEIGHT - pauseHeight - 30;
    @Getter
    private static Texture map;
    private final HUD hud;
    @Getter
    private static Array<Effect> topLayerEffects;
    @Getter
    private static Array<Effect> bottomLayerEffects;
    @Getter
    private static Array<Bullet> bullets;
    private boolean isPaused = false;
    Vector2 center = new Vector2((float) Constants.SCREEN_WIDTH / 2, (float) Constants.SCREEN_HEIGHT / 2);

    Vector2 translateV;

    public IngameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.player = GameManager.getInstance().getCurrentPlayer();
        hud = new HUD();
        SlowMo.deactivateAll();

        timeToSpawnEnemies = 5000;
        topLayerEffects = new Array<>();
        bottomLayerEffects = new Array<>();
        bullets = new Array<>();
        this.spawner = new Spawner(maxEnemyAmount, maxEnemySpawnAtOnce);
        this.btnPause = new Button(pauseX, pauseY, pauseWidth, pauseHeight, Constants.PAUSE_ICON_INACTIVE_PATH, Constants.PAUSE_ICON_ACTIVE_PATH);
        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, cam);

        Gdx.input.setInputProcessor(new IngameInputHandler());
        ConstantSound.getInstance().bgmIngame.setVolume(ConstantSound.getInstance().getBgmVolume());
        ConstantSound.getInstance().bgmIngame.play();

        map = new Texture(Constants.MAP_PATH);
    }

    @Override
    public void show() {
        spawner.spawnPlayer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MyGdxGame.batch.setProjectionMatrix(cam.combined);
        if (!isPaused) {
            renderGame();
            if (btnPause.isClicked()) {
                btnPause.setClicked(false);
                setPaused(true);
                myGdxGame.setScreen(new PauseScreen(myGdxGame, this));
            }
        }
    }

    public void renderGame() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spawnTimeShorten = (float) player.getLevel() / 15 * 1000;

        if (GameManager.getInstance().getEnemies().isEmpty() || System.currentTimeMillis() - lastEnemySpawntime >= timeToSpawnEnemies - spawnTimeShorten) {
            lastEnemySpawntime = System.currentTimeMillis();
            spawner.spawnEnemy();
        }

        MyGdxGame.batch.setProjectionMatrix(cam.combined);

        MyGdxGame.batch.begin();
        if (player.isDead()) MyGdxGame.batch.setColor(Color.LIGHT_GRAY);

        MyGdxGame.batch.draw(map, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        if (ShockWave.getInstance().enabled) ShockWave.getInstance().draw();

        updateBtnQPause();

        for (int i = 0; i < bottomLayerEffects.size; ++i) {
            Effect tmp = bottomLayerEffects.get(i);
            tmp.draw();
            if (tmp.isFinished()) bottomLayerEffects.removeIndex(i);
        }

        updateBullets();

        for (int i = 0; i < bullets.size; ++i) {
            Bullet tmp = bullets.get(i);
            if (player.isVulnerable() && tmp.getHitbox().overlaps(player.getHitbox())) {
                player.takeDamage(tmp.getDamage());
                addTopEffect(new Slice(player.getX(), player.getY(), 45, player.getHeight(), SLICE_COLOR.WHITE));
                bullets.removeIndex(i);
            }
        }

        for (int i = 0; i < GameManager.getInstance().getEnemies().size; ++i) {
            Enemy tmp = GameManager.getInstance().getEnemies().get(i);
            tmp.draw(MyGdxGame.batch);
            if(tmp.isVulnerable() && player.isAttacking() && player.getAttack().hit(tmp)){
                Rumble.rumble();
                player.getAttack().addHitEntity(tmp);
            }

            if(player.isVulnerable() && tmp.isAttacking() && tmp.getAttack().hit(player)){
                tmp.getAttack().addHitEntity(player);
            }

            if(!tmp.isExist()){
                GameManager.getInstance().getEnemies().removeIndex(i);
            }
        }

        GameManager.getInstance().getCurrentPlayer().draw(MyGdxGame.batch);

        for(int i = 0; i < topLayerEffects.size; ++i){
            Effect tmp = topLayerEffects.get(i);
            tmp.draw();
            if(tmp.isFinished()) topLayerEffects.removeIndex(i);
        }

        if(Rumble.isRumbling() && cam.position.equals(center)){
            translateV = Rumble.tick(Gdx.graphics.getDeltaTime());
            cam.translate(translateV);
            hud.cam.translate(translateV);
        }
        else{
            cam.position.set(center, 0);
            hud.cam.position.set(center, 0);
        }
        hud.cam.update();
        cam.update();

        MyGdxGame.batch.draw(player.getFrame(), 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        MyGdxGame.batch.end();

        hud.draw();

        if(Debugger.debugMode == DEBUG_MODE.ON) Debugger.getInstance().debug();

        if(!GameManager.getInstance().getCurrentPlayer().isExist()){
            this.dispose();
            myGdxGame.setScreen(new Fired(myGdxGame));
        }
    }

    public static void addTopEffect(Effect eff){ topLayerEffects.add(eff); }

    public static void addBottomEffect(Effect eff){ bottomLayerEffects.add(eff); }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public static void addBullet(Bullet bullet){ bullets.add(bullet); }

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
        btnPause.dispose();
    }
    public void updateBtnQPause(){
        btnPause.update();
        btnPause.draw(MyGdxGame.batch);
        if(btnPause.isClicked()){
            btnPause.setClicked(false);
            myGdxGame.setScreen(new PauseScreen(myGdxGame, this));
        }
    }

    public void updateBullets(){
        for(Bullet bullet : bullets){
            bullet.move();
            Rectangle thisHitbox = bullet.getHitbox();

            if(thisHitbox.x < 0 || thisHitbox.x > Constants.SCREEN_WIDTH || thisHitbox.y < 0
                    || thisHitbox.y > Constants.SCREEN_HEIGHT) bullets.removeValue(bullet, false);
            else MyGdxGame.batch.draw(bullet.getTexture(),
                    thisHitbox.x,
                    thisHitbox.y,
                    thisHitbox.width / 2,
                    thisHitbox.height / 2,
                    thisHitbox.width,
                    thisHitbox.height,
                    1,
                    1,
                    bullet.getRotation(),
                    0,
                    0,
                    (int)thisHitbox.width,
                    (int)thisHitbox.height,
                    bullet.isFlip(),
                    false);
        }
    }
}