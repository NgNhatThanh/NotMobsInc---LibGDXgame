package btck.com.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

    protected Party party;
    protected int health;
    protected int x, y;
    protected int width, heigth;
    protected int speed;
    protected boolean attacking;
    protected boolean dead;
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void update();

}
