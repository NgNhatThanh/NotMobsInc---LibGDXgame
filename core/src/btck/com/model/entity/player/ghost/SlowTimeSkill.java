package btck.com.model.entity.player.ghost;

import btck.com.common.Constants;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import btck.com.crowd_control.SlowMo;
import btck.com.model.entity.Entity;
import btck.com.view.screens.Rumble;
import btck.com.view.screens.ShockWave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class SlowTimeSkill extends Skill {

    float existTime = 4;
    float curTime;

    public SlowTimeSkill(Entity owner, int slot) {
        super(owner, slot);

        this.state = SKILL_STATE.AVAILABLE;
        this.cooldown = 10;
        this.availableTT = new Texture(Gdx.files.internal("atlas/skill/slowtime/available.png"));
        this.lockedTT = new Texture(Gdx.files.internal("atlas/skill/slowtime/locked.png"));
        this.FRAME_DURATION = Constants.FRAME_DURATION[0];
        this.atlas = new TextureAtlas(Gdx.files.internal("atlas/skill/slowtime/active.atlas"));
        this.activeAni = new Animation<>(FRAME_DURATION, atlas.findRegions("active"));
        this.hitbox = new Rectangle();
    }

    public void activate(){
        this.state = SKILL_STATE.ACTIVE;
        curTime = 0;
        ShockWave.getInstance().start(owner.getX(), owner.getY());
        Rumble.rumble();
        SlowMo.activateAll();
    }

    public void updateHitBox(){
        curTime += Gdx.graphics.getDeltaTime();
        if(curTime >= existTime) end();
    }

    public void upgrade(){
        if(state == SKILL_STATE.LOCKED){
            this.state = SKILL_STATE.AVAILABLE;
        }
        else{
            existTime++;
            cooldown--;
        }
    }

    public void end(){
        this.state = SKILL_STATE.COOLDOWN;
        cooldownRemain = cooldown;
        if(SlowMo.activeAll) SlowMo.deactivateAll();
    }

}
