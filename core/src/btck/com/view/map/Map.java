package btck.com.view.map;

import btck.com.model.entity.Enemy;
import btck.com.model.entity.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public abstract class Map {

    Player player;
    List<Enemy> enemies;

    abstract void drawEntities(SpriteBatch spriteBatch);
}
