package screens;

import btck.com.MyGdxGame;
import btck.com.common.io.sound.ConstantSound;
import btck.com.common.io.Constants;
import btck.com.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingScreen implements Screen {
    float FRAME_SPEED = 0.15f;
    Stage stage;
    Skin skin;
    MyGdxGame myGdxGame;
    Table table;
    Skin customSkin;
    private BitmapFont customFont;
    public static Slider bgmSlider;
    public static Slider soundSlider;
    Slider.SliderStyle sliderStyle;
    Label.LabelStyle labelStyle;

    protected TextureAtlas textureAtlas;
    protected Animation<TextureRegion>[] animations;
    protected float statetime;
    int width, height;
    Button btnArrow;
    int arrowPositions = 50;
    int arrowEdge = 50;

    public SettingScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;

        width = Constants.SCREEN_WIDTH;
        height = Constants.SCREEN_HEIGHT;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.BACKGROUND_ATLAS));
        animations = new Animation[1];

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("loop"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();

        btnArrow = new Button(arrowPositions, arrowPositions, arrowEdge, arrowEdge, Constants.BACK_ARROW_INACTIVE_ICON_PATH, Constants.BACK_ARROW_ACTIVE_ICON_PATH);

        customSkin = new Skin();

        customSkin.add("slider", new Texture(Gdx.files.internal("uiskin/slider.png")));
        customSkin.add("knob", new Texture(Gdx.files.internal("uiskin/knob.png")));

        sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = customSkin.getDrawable("slider");
        sliderStyle.knob = customSkin.getDrawable("knob");

        initFont();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;

        adjustBgm();
        adjustSound();

    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HUD/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        params.color = new Color(147 /255.0f, 0, 0, 1.0f);
        customFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statetime += Gdx.graphics.getDeltaTime();

        // Bắt đầu vẽ batch
        myGdxGame.batch.begin();

        myGdxGame.batch.draw(animations[0].getKeyFrame(statetime, true), 0, 0, width, height);
        updateArrow();
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
        btnArrow.dispose();
        stage.dispose();
    }

    public void updateArrow(){
        btnArrow.update();
        btnArrow.draw(myGdxGame.batch);
        if(btnArrow.isClicked()){
            btnArrow.setClicked(false);
            this.dispose();
            System.out.println(Constants.inited);
            myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
        }
    }

    public void adjustBgm(){
        Label lBgm = new Label("Music", labelStyle);
        table.setFillParent(true);
        stage.addActor(table);

        table.add(lBgm);
        bgmSlider = new Slider(0, 1, 0.1f, false, sliderStyle);
        table.add(bgmSlider).width(500).padLeft(10);
        bgmSlider.setValue(ConstantSound.getInstance().getBgmVolume());
        bgmSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ConstantSound.getInstance().setBgmVolume(bgmSlider.getValue());
                ConstantSound.getInstance().bgmMenu.setVolume(bgmSlider.getValue());
                ConstantSound.getInstance().bgmIngame.setVolume(bgmSlider.getValue());
            }
        });
    }

    public void adjustSound(){
        table.row();
        soundSlider = new Slider(0, 1, 0.1f, false, sliderStyle);
        Label lSound = new Label("Sound", labelStyle);
        table.setFillParent(true);

        table.add(lSound).padTop(50);
        table.add(soundSlider).width(500).padLeft(10).padTop(50);
        soundSlider.setValue(ConstantSound.getInstance().getSoundVolume());
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ConstantSound.getInstance().setSoundVolume(soundSlider.getValue());
                ConstantSound.getInstance().slash.play(soundSlider.getValue());
            }
        });
    }
    
}
