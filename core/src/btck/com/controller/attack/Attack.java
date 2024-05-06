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
    }

    public abstract void start();

    public void update(float statetime){
        if(owner.isDead()) return;
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
//        System.out.println("add " + animation.getKeyFrameIndex(statetime));
        if(frameToDealDamageIdx >= frameToDealDamage.length || animation.getKeyFrameIndex(statetime) != frameToDealDamage[frameToDealDamageIdx]) return;
        if(hitEntities.contains(entity, false)) return;
        hitEntities.add(entity);
    }

    public abstract void updateHitbox();

    public boolean hit(Entity entity){
        updateHitbox();
        return this.hitbox.overlaps(entity.getHitbox());
    }

    public void end(){
        statetime = 0;
        frameToDealDamageIdx = 0;
        hitEntities.clear();
        if(dealed != null) for(boolean deal : dealed) deal = false;
    }

}
