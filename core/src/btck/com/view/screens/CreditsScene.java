package btck.com.view.screens;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class CreditsScene implements Screen {

    public static final int creditWidth = 700;
    public static final int creditHeight = 350;

    MyGdxGame myGdxGame;
    Texture creditPng;
    Button btnArrow;
    int arrowPositions = 50;
    int arrowEdge = 50;

    public CreditsScene(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        creditPng = new Texture(Constants.CREDIT_IMG_PATH);
        btnArrow = new Button(arrowPositions, arrowPositions, arrowEdge, arrowEdge, Constants.BACK_ARROW_INACTIVE_ICON_PATH, Constants.BACK_ARROW_ACTIVE_ICON_PATH);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGdxGame.batch.begin();

        updateArrow();
        myGdxGame.batch.draw(creditPng, (Constants.SCREEN_WIDTH - creditWidth) / 2, (Constants.SCREEN_HEIGHT - creditHeight) / 2, creditWidth, creditHeight);


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
    }

    public void updateArrow(){
        btnArrow.update();
        btnArrow.draw(myGdxGame.batch);
        if(btnArrow.isClicked()){
            btnArrow.setClicked(false);
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
        }
    }
}
