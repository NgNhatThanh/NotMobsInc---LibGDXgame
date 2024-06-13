package btck.com.model.entity;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.skill.Skill;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
//import com.sun.source.tree.WhileLoopTree;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity{

    protected Party party;
    protected int currentHealth, maxHealth;
    protected float x, y;
    protected int attackX, attackY;
    public int width, height;
    public float normalSpeed;
    public float attackSpeed;
    public float currentSpeed;
    public float xSpeed, ySpeed;
    protected boolean visible = true;
    protected boolean vulnerable = false;
    protected boolean attacking;
    protected boolean flip;
    protected boolean dead;
    protected boolean exist = true;
    protected Rectangle hitbox;
    protected boolean isHit;
    protected Attack attack;
    protected float angle;
    protected TextureAtlas atlas;
    protected Animation<TextureRegion>[] animations;
    protected float FRAME_DURATION;
    protected float statetime;
    protected int animationIdx;
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void attack(int x, int y);
    public abstract void move(float desX, float desY);

    public void takeDamage(int damage){
        this.currentHealth -= damage;
        if(this.currentHealth < 0) this.currentHealth = 0;
        update();
    }

    public void update(){
        if(currentHealth <= 0){
            attacking = false;
            vulnerable = false;
            dead = true;
            statetime = 0;
            animationIdx = 3;
        }
    }

    public void setFRAME_DURATION(float FD){
        this.FRAME_DURATION = FD;
        for(Animation<TextureRegion> animation : animations) animation.setFrameDuration(FD);
    }

}
