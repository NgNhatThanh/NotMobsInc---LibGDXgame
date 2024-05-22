package btck.com.view.hud;

import btck.com.model.constant.Constants;
import btck.com.model.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelDisplay extends Actor {
    private Stage stage;
    private Viewport viewport;
    private BitmapFont customFont;
    private Player player;
    private GlyphLayout layout;

    public LevelDisplay(Player player) {
        this.player = player;
        this.layout = new GlyphLayout();
        viewport = new FitViewport(Constants.screenWidth, Constants.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        initFont();
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HUD/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 28;
        params.color = Color.WHITE;
        customFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        String labelText = "LEVEL " + player.getLevel();
        layout.setText(customFont, labelText);
        customFont.draw(batch, labelText, 330, Constants.screenHeight - layout.height - 35);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        customFont.dispose();
    }
}