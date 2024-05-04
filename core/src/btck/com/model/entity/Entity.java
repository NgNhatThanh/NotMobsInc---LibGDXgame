package btck.com.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.sun.source.tree.WhileLoopTree;
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
    public int damage;
    protected boolean attacking;
    protected boolean flip;
    protected boolean dead;
    protected boolean exist = true;
    protected Rectangle hitbox;
    protected boolean isHit;
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void update();
    public abstract void attack(int x, int y);

    protected Array<Entity> hitEntities;
    protected Array<Entity> takeDameEntities;

    public void addHitEntity(Entity entity){
        if(hitEntities.contains(entity, false)) return;
//        entity.takeDamage(this.damage);
        hitEntities.add(entity);
    }


    public void takeDamage(int damage){
        this.health -= damage;
    }
    public boolean hit(Entity entity){
        return this.hitbox.overlaps(entity.hitbox);
    }

}
