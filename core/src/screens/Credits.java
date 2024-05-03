package screens;

import btck.com.MyGdxGame;
import btck.com.model.constant.GameConstant;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Credits implements Screen {

    public static final int creditWidth = 700;
    public static final int creditHeight = 350;
    public static final int arrowEdge = 50;
    MyGdxGame myGdxGame;

    Texture creditPng;
    Texture arrowInactive;
    Texture arrowActive;
    public Credits(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        creditPng = new Texture("credits\\credits.png");
        arrowActive = new Texture("credits\\back arrow.png");
        arrowInactive = new Texture("credits\\back arrow inactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGdxGame.batch.begin();

        drawArrow();
        myGdxGame.batch.draw(creditPng, (MainMenuScreen.WIDTH - creditWidth) / 2, (MainMenuScreen.HEIGHT - creditHeight) / 2, creditWidth, creditHeight);

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
        arrowInactive.dispose();
        arrowActive.dispose();
        creditPng.dispose();
    }
    
    public void drawArrow(){
        int arrowPositions = 50;
        if(Gdx.input.getX() < arrowPositions + arrowEdge && Gdx.input.getX() > arrowPositions && GameConstant.screenHeight - Gdx.input.getY() < arrowPositions + arrowEdge && GameConstant.screenHeight - Gdx.input.getY() > arrowPositions){
            myGdxGame.batch.draw(arrowActive, arrowPositions, arrowPositions, arrowEdge, arrowEdge);
            if(Gdx.input.isTouched()){
                this.dispose();
                myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(arrowInactive, arrowPositions, arrowPositions, arrowEdge, arrowEdge);
        }
    }
}
