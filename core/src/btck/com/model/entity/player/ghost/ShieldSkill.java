package btck.com.model.entity.player.ghost;

import btck.com.common.Constants;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class ShieldSkill extends Skill {

    float lastTime = 5;
    float curTime;

    public ShieldSkill(Entity owner, int slot) {
        super(owner, slot);

        this.cooldown = 10;
        this.availableTT = new Texture(Gdx.files.internal(""));
        this.lockedTT = new Texture(Gdx.files.internal("atlas/skill/shield/locked.png"));
        this.FRAME_DURATION = Constants.FRAME_DURATION[0];
        this.atlas = new TextureAtlas(Gdx.files.internal(""));
        this.activeAni = new Animation<>(FRAME_DURATION, atlas.findRegions("active"));
        this.hitbox = new Rectangle();
    }

    public void activate(){
        this.state = SKILL_STATE.ACTIVE;
        owner.setVulnerable(false);
        curTime = 0;
    }

    public void updateHitBox(){
        hitbox.x = owner.getX();
        hitbox.y = owner.getY();

        curTime += Gdx.graphics.getDeltaTime();
        if(curTime >= lastTime) end();
    }

    public void upgrade(){
        if(this.state == SKILL_STATE.LOCKED) this.state = SKILL_STATE.AVAILABLE;
        else{
            this.cooldown--;
            this.lastTime++;
        }
        hitbox.width = owner.getWidth();
        hitbox.height = owner.getHeight();
    }

    public void end(){
        this.state = SKILL_STATE.COOLDOWN;
    }

}
