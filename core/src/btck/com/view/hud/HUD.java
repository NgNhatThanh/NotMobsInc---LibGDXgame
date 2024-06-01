package btck.com.view.hud;

import btck.com.common.GameManager;
import btck.com.MyGdxGame;
import btck.com.common.Constants;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD{

    public Stage stage;
    HealthBar healthBar;
    LevelLabel levelLabel;

    Viewport viewport;

    public HUD(){
        viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        stage = new Stage(viewport, MyGdxGame.batch);

        //         Thêm Health Bar vào stage
        this.healthBar = new HealthBar(GameManager.getInstance().getCurrentPlayer());
        healthBar.setSize(300, 30);
        healthBar.setPosition(Constants.SCREEN_WIDTH - healthBar.getWidth() - 180, 30);
        stage.addActor(healthBar);

        // thêm Level
        this.levelLabel = new LevelLabel(GameManager.getInstance().getCurrentPlayer());
        stage.addActor(levelLabel);
    }

    public void dispose(){
        this.stage.dispose();
    }

    public void draw(){
        this.stage.draw();
    }

}
