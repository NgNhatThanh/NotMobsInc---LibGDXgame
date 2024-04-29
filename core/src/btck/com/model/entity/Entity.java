package btck.com.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {

    protected Party party;
    protected int health;
    protected float x, y;
    protected int attackX, attackY;
    public int width, height;
    public int speed;
    protected boolean attacking;
    protected boolean flip;
    protected boolean dead;
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void update();
    public abstract void attack(int x, int y);

}
