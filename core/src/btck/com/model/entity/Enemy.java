package btck.com.model.entity;

public abstract class Enemy extends Entity{
    public int exp;
    public abstract void move(float x, float y);

    public int getExp() {
        return exp;
    }
}
