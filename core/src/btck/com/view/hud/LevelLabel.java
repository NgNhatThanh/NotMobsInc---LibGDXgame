package btck.com.view.hud;

import btck.com.common.Constants;
import btck.com.model.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelLabel extends Actor {
    private BitmapFont customFont;
    private Player player;
    private GlyphLayout layout;

    public LevelLabel(Player player) {
        this.player = player;
        this.layout = new GlyphLayout();
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
        String labelText = "LEVEL " + player.getLevel();
        layout.setText(customFont, labelText);
        customFont.draw(batch, labelText, 330, Constants.SCREEN_HEIGHT - layout.height - 40);
    }

    public void dispose() {
        customFont.dispose();
    }
}