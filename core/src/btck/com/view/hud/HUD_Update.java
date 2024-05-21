package btck.com.view.hud;

import btck.com.model.constant.Constants;
import btck.com.model.entity.Player;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD_Update {
    public Stage stage;
    private Viewport viewport;
    private HUD_Actor hudActor;

    public HUD_Update(SpriteBatch spriteBatch, Player player) {
        viewport = new FitViewport(Constants.screenWidth, Constants.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        hudActor = new HUD_Actor(player);
        hudActor.setSize(200, 20);
        hudActor.setPosition(Constants.screenWidth - 220, Constants.screenHeight - 40);

        stage.addActor(hudActor);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        hudActor.dispose();
    }
}
