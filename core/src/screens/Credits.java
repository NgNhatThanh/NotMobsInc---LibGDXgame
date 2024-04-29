package screens;

import btck.com.MyGdxGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jdk.tools.jmod.Main;

public class Credits implements Screen {

    public static final int creditWidth = 500;
    public static final int creditHeight = 250;
    public static final int arrowEdge = 50;
    MyGdxGame myGdxGame;

    Texture creditPng;
    public Credits(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        creditPng = new Texture("credits.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGdxGame.batch.begin();

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

    }
    
    public void drawArrow(){
        if(Gdx.input.getX() < newGameX + arrowEdge && Gdx.input.getX() > newGameX && GameConstant.screenHeight - Gdx.input.getY() < newGameY + newGameHeight && GameConstant.screenHeight - Gdx.input.getY() > newGameY){
            myGdxGame.batch.draw(newGameActive, newGameX, newGameY, arrowEdge, newGameHeight);
            if(Gdx.input.isTouched()){
                this.dispose();
                myGdxGame.setScreen(new IngameScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(newGameInactive, newGameX, newGameY, arrowEdge, newGameHeight);
        }
    }
}
