package btck.com.view.screens;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.common.GameManager;
import btck.com.common.sound.ConstantSound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fired implements Screen {
    private BitmapFont customFont;
    private GlyphLayout layout;
    float FRAME_SPEED = 0.08f;
    int width, height;
    protected TextureAtlas textureAtlas;
    protected Animation<TextureRegion>[] animations;
    protected int animationIdx;
    protected float statetime;
    protected boolean isPaused;
    protected float pauseTime;
    static Sound[] sfx = {Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/open door.ogg")),
                            Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/fired.ogg")),
                            Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/close door.ogg"))};
    private boolean sfx0Played = false, sfx1Played = false, sfx2Played = false;

    public Fired(){
        this.layout = new GlyphLayout();
        width = Constants.SCREEN_WIDTH;
        height = Constants.SCREEN_HEIGHT;
        textureAtlas = new TextureAtlas(Gdx.files.internal(Constants.GET_BACK_TO_WORK_ATLAS_PATH));
        animations = new Animation[4];
        isPaused = false;
        pauseTime = 0f;

        animations[0] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("open"));
        animations[1] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("level"));
        animations[2] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("tile"));
        animations[3] = new Animation<>(FRAME_SPEED, textureAtlas.findRegions("close"));

        animationIdx = 0;
        initFont();
        layout.setText(customFont, "LEVEL: " + GameManager.getInstance().getCurrentPlayer().getLevel());
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HUD/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 55;
        params.color = new Color(147 / 255.0f, 0/255.0f, 0 / 255.0f, 1.0f);
        customFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void show() {
        MyGdxGame.batch.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        if (isPaused) {
            pauseTime += delta;
            if ((animationIdx == 1 && pauseTime >= 2.0f) || (animationIdx == 2 && pauseTime >= 1.0f)) {
                isPaused = false;
                pauseTime = 0f;
                animationIdx++;
                statetime = 0f;
            }
            return; // Trở lại mà không cập nhật frame khi đang tạm dừng
        }

        statetime += Gdx.graphics.getDeltaTime();

        if (animationIdx == 1 && animations[animationIdx].isAnimationFinished(statetime)) {
            isPaused = true;
            return; // Dừng lại khi animationIdx = 1 và animation đã kết thúc
        }else if (animationIdx == 2 && animations[animationIdx].isAnimationFinished(statetime)) {
            isPaused = true;
            return; // Dừng lại khi animationIdx = 1 và animation đã kết thúc
        }

        if (animationIdx < animations.length - 1 && animations[animationIdx].isAnimationFinished(statetime)) {
            animationIdx++;
            statetime = 0f;
        }

        if(animationIdx == 0){
            if(!sfx0Played){
                sfx[0].play(ConstantSound.getInstance().getSoundVolume());
                sfx0Played = true;
            }
        }else if(animationIdx == 2){
            if(!sfx1Played){
                sfx[0].stop();
                sfx[1].play(ConstantSound.getInstance().getSoundVolume());
                sfx1Played = true;
            }
        }else if(animationIdx == 3){
            if(!sfx2Played){
                sfx[1].stop();
                sfx[2].play(ConstantSound.getInstance().getSoundVolume());
                sfx2Played = true;
            }
        }

        if (animationIdx == 3 && animations[animationIdx].isAnimationFinished(statetime)) {
            MyGdxGame.myGdxGame.setScreen(new MainMenuScreen());
        }

        MyGdxGame.batch.begin();

        MyGdxGame.batch.draw(animations[animationIdx].getKeyFrame(statetime, false), 0, 0, width, height);
        if(animationIdx == 1){
            customFont.draw(MyGdxGame.batch, layout, (Constants.SCREEN_WIDTH - layout.width)/2, (Constants.SCREEN_HEIGHT - layout.height)/2 + 125);
        }
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
}
