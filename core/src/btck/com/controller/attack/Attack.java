package btck.com.controller.attack;

import btck.com.model.entity.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;


public abstract class Attack {

    protected Entity owner;
    protected Animation<TextureRegion> animation;
    protected Array<Entity> hitEntities;
    protected DEAL_DAMAGE_TIME dealDamageType;
    @Getter
    protected int damage;
    @Getter
    @Setter
    protected int currentDamage;
    protected boolean[] dealed;
    protected int[] frameToDealDamage;
    protected int frameToDealDamageIdx;
    public long coolDown;
    public long lastAttackTime = 0;
    @Getter protected Rectangle hitbox;


    public Attack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType){
        this.owner = owner;
        this.animation = animation;
        this.dealDamageType = dealDamageType;
        hitEntities = new Array<>();
    }

    public abstract void start();

    public abstract void update(float statetime);

    public void dealDamage(){
        for(Entity entity : hitEntities){
            entity.takeDamage(this.currentDamage);
        }

        dealed[frameToDealDamageIdx] = true;
        ++frameToDealDamageIdx;
        hitEntities.clear();
    }

    public abstract void addHitEntity(Entity entity);

    public abstract void updateHitbox();

    public boolean hit(Entity entity){
        updateHitbox();
        return this.hitbox.overlaps(entity.getHitbox());
    }

    public void end(){
        owner.currentSpeed = owner.normalSpeed;
        frameToDealDamageIdx = 0;
        hitEntities.clear();
        if(dealed != null) for(boolean deal : dealed) deal = false;
    }
}
