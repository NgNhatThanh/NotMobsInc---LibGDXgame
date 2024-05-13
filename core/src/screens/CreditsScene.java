package screens;

import btck.com.MyGdxGame;
import btck.com.model.constant.Constants;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


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
        creditPng = new Texture(Constants.creditImgPath);
        btnArrow = new Button(arrowPositions, arrowPositions, arrowEdge, arrowEdge, Constants.backArrowInactiveIconPath, Constants.backArrowActiveIconPath);

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
        myGdxGame.batch.draw(creditPng, (Constants.screenWidth - creditWidth) / 2, (Constants.screenHeight - creditHeight) / 2, creditWidth, creditHeight);


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
