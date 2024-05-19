package btck.com.model.entity;

import btck.com.controller.attack.Attack;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
//import com.sun.source.tree.WhileLoopTree;
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
    public int normalSpeed;
    public int currentSpeed;
    protected boolean attacking;
    protected boolean flip;
    protected boolean dead;
    protected boolean exist = true;
    protected Rectangle hitbox;
    protected boolean isHit;
    protected Attack attack;
    protected TextureAtlas textureAtlas;
    protected Animation<TextureRegion>[] animations;
    protected Texture sampleTexture;
    protected float statetime;
    protected int animationIdx;
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void update();
    public abstract void attack(int x, int y);
    public abstract void move(float desX, float desY);

    public void takeDamage(int damage){
        this.health -= damage;
        update();
    }

}
