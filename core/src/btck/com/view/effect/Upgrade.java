package btck.com.view.effect;

import btck.com.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Upgrade extends Effect{

    static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/effect/upgrade/upgrade.atlas"));

    public Upgrade(float x, float y) {
        super(x, y, 0);
        this.FRAME_DURATION = 0.1f;
        ani = new Animation<>(FRAME_DURATION, atlas.findRegions("upgrade"));
        this.fixedSize = false;
        this.width = GameManager.getInstance().getCurrentPlayer().getWidth() + 90;
        this.heigth = GameManager.getInstance().getCurrentPlayer().getHeight() + 90;
    }

    public void draw(){
        this.x = GameManager.getInstance().getCurrentPlayer().getX() - width / 2;
        this.y = GameManager.getInstance().getCurrentPlayer().getY();
        super.draw();
    }
}
