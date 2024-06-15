package btck.com.view.screens;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class CreditsScene implements Screen {
    float FRAME_SPEED = 0.15f;

    public static final int creditWidth = 700;
    public static final int creditHeight = 350;

    Texture creditPng;
    Button btnArrow;
    Button btnGithub;
    Button btnMobinc;

    protected TextureAtlas textureAtlas;
    protected Animation<TextureRegion>[] animations;
    protected float statetime;
    int width, height;
    int arrowPositions = 50;
    int arrowEdge = 50;
    int githubPositon = 250;
    int githubEgde = 75;
    int mobincWidth = 350;
    int mobincHeight = 37;

    public CreditsScene(){
        creditPng = new Texture(Constants.CREDIT_IMG_PATH);
        btnArrow = new Button(arrowPositions, arrowPositions, arrowEdge, arrowEdge, Constants.BACK_ARROW_INACTIVE_ICON_PATH, Constants.BACK_ARROW_ACTIVE_ICON_PATH);
        btnGithub = new Button(githubPositon, githubPositon, githubEgde, githubEgde, Constants.GITHUB_INACTIVE_PATH, Constants.GITHUB_INACTIVE_PATH);
        btnMobinc = new Button((Constants.SCREEN_WIDTH - mobincWidth)/2, 20, mobincWidth, mobincHeight, Constants.MOBINC_INACTIVE_PATH, Constants.MOBINC_ACTIVE_PATH);
        width = Constants.SCREEN_WIDTH;
        height = Constants.SCREEN_HEIGHT;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.BACKGROUND_ATLAS));
        animations = new Animation[1];

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("loop"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statetime += Gdx.graphics.getDeltaTime();

        MyGdxGame.batch.begin();

        MyGdxGame.batch.draw(animations[0].getKeyFrame(statetime, true), 0, 0, width, height);
        updateArrow();
        updateIcon();
        MyGdxGame.batch.draw(creditPng, (Constants.SCREEN_WIDTH - creditWidth) / 2 - 100, (Constants.SCREEN_HEIGHT - creditHeight) / 2 + 100, creditWidth, creditHeight);


        MyGdxGame.batch.end();
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

    public void updateArrow(){
        btnArrow.update();
        btnArrow.draw();
        if(btnArrow.isClicked()){
            btnArrow.setClicked(false);
            MyGdxGame.myGdxGame.setScreen(new MainMenuScreen());
        }
    }
    public void updateIcon(){
        btnGithub.update();
        btnGithub.draw();
        if(btnGithub.isClicked()){
            btnGithub.setClicked(false);
            Gdx.net.openURI("https://github.com/NgNhatThanh/ProGamejam_BTCK");
        }
        btnMobinc.update();
        btnMobinc.draw();
        if(btnMobinc.isClicked()){
            btnMobinc.setClicked(false);
            Gdx.net.openURI("https://overboy.itch.io/mobs-inc");
        }
    }
}
