package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.Attack;
import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.Player;
import btck.com.view.effect.Slice;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class DashAttack extends Attack {

    final int DASH_SPEED = 600;

    float attackX, attackY;

    Array<Slice> sliceEffects;

    TextureAtlas slices;

    Texture sliceSample;

    float angle;

    int sliceWidth;

    public DashAttack(Animation<TextureRegion> animation, Entity owner, DEAL_DAMAGE_TIME dealDamageType) {
        super(animation, owner, dealDamageType);
        sliceEffects = new Array<>();
        slices = new TextureAtlas(Gdx.files.internal("atlas/player/ghost1/slice.atlas"));
        hitbox = new Rectangle();
        hitbox.width = owner.width;
        hitbox.height = owner.height;
        damage = 2;

        sliceSample = new Texture(Gdx.files.internal("atlas/player/ghost1/slice_sample.png"));
        sliceWidth = sliceSample.getWidth();
        sliceSample.dispose();
    }

    @Override
    public void start() {
        owner.currentSpeed = DASH_SPEED;
        attackX = owner.getAttackX();
        attackY = owner.getAttackY();
        this.angle = owner.getAngle();
        owner.move(attackX, attackY);
    }

    @Override
    public void update(float statetime) {
        updateEff();
    }

    void updateEff(){
        for(Iterator<Slice> eff = sliceEffects.iterator(); eff.hasNext(); ){
            Slice tmp = eff.next();
            tmp.draw();
            if(tmp.isFinish()) eff.remove();
        }
    }

    public void addHitEntity(Entity entity){
        updateHitbox();
        if(hitEntities.contains(entity, false)) return;
        entity.takeDamage(this.damage);
        sliceEffects.add(new Slice(slices.findRegions("slice"),
                entity.getX() - sliceWidth / 2,
                entity.getY() + entity.getHeight() / 2,
                   angle));
        if(entity.isDead()) ((Player) owner).currentExp += ((Enemy)entity).exp;
        hitEntities.add(entity);
    }

    @Override
    public void updateHitbox() {
        hitbox.x = owner.getX() - owner.getWidth() / 2;
        hitbox.y = owner.getY();
    }
}
