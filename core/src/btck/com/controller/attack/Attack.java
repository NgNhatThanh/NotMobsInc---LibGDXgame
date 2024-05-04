package btck.com.controller.attack;

import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;


public abstract class Attack {

    protected Entity owner;
    protected Animation<TextureRegion> animation;
    protected Array<Entity> hitEntities;
    protected DEAL_DAMAGE_TIME dealDamageType;
    @Getter protected int damage;
    protected boolean[] dealed;
    protected int[] frameToDealDamage;
    protected int frameToDealDamageIdx;
    protected Rectangle hitbox;
    protected float statetime;

    public Attack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType){
        this.owner = owner;
        this.animation = animation;
        this.dealDamageType = dealDamageType;
        hitEntities = new Array<>();

        hitbox = owner.getHitbox(); // just to test
    }

    public abstract void start();

    public void update(float statetime){
//        System.out.println(owner + " " + animation.getKeyFrameIndex(statetime));
        if(frameToDealDamageIdx < frameToDealDamage.length && animation.getKeyFrameIndex(statetime) == frameToDealDamage[frameToDealDamageIdx]){
            dealDamage();
        }
    }

    public void dealDamage(){
        for(Entity entity : hitEntities){
            entity.takeDamage(this.damage);
        }

        dealed[frameToDealDamageIdx] = true;
        ++frameToDealDamageIdx;
        hitEntities.clear();
    }

    public void addHitEntity(Entity entity){
        statetime += Gdx.graphics.getDeltaTime();
        if(frameToDealDamageIdx >= frameToDealDamage.length || animation.getKeyFrameIndex(statetime) != frameToDealDamage[frameToDealDamageIdx]) return;
        if(hitEntities.contains(entity, false)) return;
        hitEntities.add(entity);
    }

    public boolean hit(Entity entity){
        return this.hitbox.overlaps(entity.getHitbox());
    }

    public void end(){
        statetime = 0;
        frameToDealDamageIdx = 0;
        hitEntities.clear();
        for(boolean deal : dealed) deal = false;
    }

}
