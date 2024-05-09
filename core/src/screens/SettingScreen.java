package screens;

import btck.com.MyGdxGame;
import btck.com.model.constant.ConstantSound;
import btck.com.model.constant.GameConstant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingScreen implements Screen {
    Stage stage;
    Skin skin;
    MyGdxGame myGdxGame;
    Table table;
    public static Slider bgmSlider;
    public static Slider soundSlider;

    public SettingScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
        table = new Table();

        adjustBgm();
        adjustSound();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Bắt đầu vẽ batch
        myGdxGame.batch.begin();

        drawArrow();

        myGdxGame.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

        stage.dispose();
    }

    public void drawArrow(){
        int arrowPositions = 50;
        if(Gdx.input.getX() < arrowPositions + CreditsScene.arrowEdge && Gdx.input.getX() > arrowPositions && GameConstant.screenHeight - Gdx.input.getY() < arrowPositions + CreditsScene.arrowEdge && GameConstant.screenHeight - Gdx.input.getY() > arrowPositions){
            myGdxGame.batch.draw(CreditsScene.arrowActive, arrowPositions, arrowPositions, CreditsScene.arrowEdge, CreditsScene.arrowEdge);
            if(Gdx.input.isTouched()){
                this.dispose();
                myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            }
        }else{
            myGdxGame.batch.draw(CreditsScene.arrowInactive, arrowPositions, arrowPositions, CreditsScene.arrowEdge, CreditsScene.arrowEdge);
        }
    }

    public void adjustBgm(){
        Label lBgm = new Label("Music", skin);
        table.setFillParent(true);
        stage.addActor(table);

        table.add(lBgm);
        bgmSlider = new Slider(0, 1, 0.1f, false, skin);
        table.add(bgmSlider).width(500).padLeft(10);
        bgmSlider.setValue(ConstantSound.bgm.getVolume());
        bgmSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ConstantSound.bgm.setVolume(bgmSlider.getValue());
                ConstantSound.bgmIngame.setVolume(bgmSlider.getValue());
            }
        });
    }

    public void adjustSound(){
        table.row();
        soundSlider = new Slider(0, 1, 0.1f, false, skin);
        Label lSound = new Label("Sound", skin);

        table.setFillParent(true);

        table.add(lSound);
        table.add(soundSlider).width(500).padLeft(10);
        soundSlider.setValue(ConstantSound.getSoundVolume());
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ConstantSound.setSoundVolume(soundSlider.getValue());
                ConstantSound.slash.play(ConstantSound.getSoundVolume());
            }
        });
    }
}
