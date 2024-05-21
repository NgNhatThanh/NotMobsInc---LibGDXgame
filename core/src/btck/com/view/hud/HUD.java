package btck.com.view.hud;

import btck.com.GameManager;
import btck.com.model.constant.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class    HUD implements Disposable {
    public Stage stage;
    Viewport viewport;

    int level;
    int health;

    Label levelLabel;
    Label healthLabel;
    Label levelNumLabel;
    Label healthNumLabel;

    Table table;

    public HUD(SpriteBatch spriteBatch){
        health = GameManager.getInstance().getCurrentPlayer().getHealth();
        level = 0;

        viewport = new FitViewport(Constants.screenWidth, Constants.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelNumLabel = new Label(Integer.toString(level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthNumLabel = new Label(Integer.toString(health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(levelLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.row();
        table.add(levelNumLabel).expandX();
        table.add(healthNumLabel).expandX();

        stage.addActor(table);
    }

    public void update(){
        levelNumLabel.setText(GameManager.getInstance().getCurrentPlayer().level);
        healthNumLabel.setText(GameManager.getInstance().getCurrentPlayer().getHealth());
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
