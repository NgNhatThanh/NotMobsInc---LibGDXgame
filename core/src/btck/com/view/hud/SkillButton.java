package btck.com.view.hud;

import btck.com.common.Constants;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SkillButton extends Actor {

    Skill skill;

    SKILL_STATE state;

    float x;

    float cooldownRemain, cooldown;

     final float skillButtonWidth = 100, skillButtonHeight = 88;

    float statetime;

    Texture cooldownTT;

    TextureAtlas atlas;

    Animation<TextureRegion> activeAni;

    float frameDuration;

    public SkillButton(Skill skill, int slot){
        this.skill = skill;

        this.cooldown = skill.getCooldown();
        this.frameDuration = Constants.FRAME_DURATION[0];
        cooldownTT = new Texture(Gdx.files.internal("atlas/skill/cooldown.png"));
        atlas = new TextureAtlas(Gdx.files.internal("atlas/skill/active.atlas"));
        activeAni = new Animation<>(frameDuration, atlas.findRegions("active"));
        switch (slot){
            case 1 :
                x = 513;
                break;
            case 2 :
                x = 652;
                break;
            case 3 :
                x = 791;
                break;
        }
    }

    public void draw(Batch batch, float parentAlpha){
        state = skill.getState();

        if(state == SKILL_STATE.LOCKED) batch.draw(skill.getLockedTT(), x, 0, skillButtonWidth, skillButtonHeight);
        else{
            batch.draw(skill.getAvailableTT(), x, 0, skillButtonWidth, skillButtonHeight);

            switch (state){
                case ACTIVE:
                    cooldownRemain = cooldown;
                    statetime += Gdx.graphics.getDeltaTime();
                    batch.draw(activeAni.getKeyFrame(statetime, true), x, 0, skillButtonWidth, skillButtonHeight);
                    break;
                case COOLDOWN:
                    cooldownRemain -= Gdx.graphics.getDeltaTime();
                    if(cooldownRemain <= 0) skill.setState(SKILL_STATE.AVAILABLE);
                    else{
                        float tmp = cooldownRemain / cooldown;
                        batch.draw(cooldownTT, x, 0, skillButtonWidth, skillButtonHeight * tmp);
                    }
                    break;
            }
        }
    }

    public void activate(){
        if(skill.getState() == SKILL_STATE.AVAILABLE) skill.activate();
    }

}
