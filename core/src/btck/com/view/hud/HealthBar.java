package btck.com.view.hud;
import btck.com.model.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBar extends Actor {
    private ShapeRenderer shapeRenderer;
    private BitmapFont customFont;
    private GlyphLayout layout;
    private Player player;

    public HealthBar(Player player) {
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.layout = new GlyphLayout();
        initFont();
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HUD/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        params.color = Color.WHITE;
        customFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Dừng batch trước khi vẽ với ShapeRenderer
        batch.end();

        // Vẽ nền của thanh máu (màu xám đậm)
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        // Vẽ thanh máu hiện tại (màu đỏ)
        float healthPercentage = (float) player.getCurrentHealth() / player.getMaxHealth();
        shapeRenderer.setColor(new Color(0.56f, 0.18f, 0.18f, 1f));
        shapeRenderer.rect(getX(), getY(), getWidth() * healthPercentage, getHeight());

        shapeRenderer.end();

        // Bắt đầu lại batch sau khi sử dụng ShapeRenderer
        batch.begin();

        // Vẽ số máu hiện tại và tối đa
        String healthText = player.getCurrentHealth() + " / " + player.getMaxHealth();
        layout.setText(customFont, healthText);
        customFont.draw(batch, layout, getX() + (getWidth() - layout.width) / 2, getY() + getHeight() / 2 + layout.height / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public void dispose() {
        shapeRenderer.dispose();
        customFont.dispose();
    }
}
