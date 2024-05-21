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

public class HUD_Actor extends Actor {
    private ShapeRenderer shapeRenderer;
    private BitmapFont customFont;
    private GlyphLayout layout;
    private Player player;

    public HUD_Actor(Player player) {
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.layout = new GlyphLayout();
        initFont();
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HUD/Minecraft.ttf")); // Đường dẫn tới tệp font mới
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
        float healthPercentage = (float) player.getHealth() / player.getMaxHealth();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth() * healthPercentage, getHeight());

        shapeRenderer.end();

        // Bắt đầu lại batch sau khi sử dụng ShapeRenderer
        batch.begin();

        // Vẽ số máu hiện tại và tối đa
        String healthText = player.getHealth() + " / " + player.getMaxHealth();
        layout.setText(customFont, healthText);
        customFont.draw(batch, layout, getX() + (getWidth() - layout.width) / 2, getY() + getHeight() / 2 + layout.height / 2);
        // Vẽ "HealthBar"
        String labelText = "Health Bar";
        layout.setText(customFont, labelText);
        customFont.draw(batch, layout, getX() - layout.width - 10, getY() + getHeight() / 2 + layout.height / 2);

        // Vẽ "Level"
        String levelText = "Level: " + player.getLevel();
        layout.setText(customFont, levelText);
        customFont.draw(batch, layout, 10, getY() + getHeight() / 2 + layout.height / 2);
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
