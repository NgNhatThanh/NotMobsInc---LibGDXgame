package btck.com.controller.attack.skill;

import btck.com.MyGdxGame;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill implements InputProcessor {

    protected float statetime;
    protected float FRAME_DURATION;
    protected Entity owner;
    protected int slot;
    protected float cooldown;
    protected float cooldownRemain;
    protected int damage;
    protected SKILL_STATE state = SKILL_STATE.LOCKED;
    protected Texture lockedTT;
    protected Texture availableTT;
    protected Texture cooldownTT;
    protected TextureAtlas atlas;
    protected Animation<TextureRegion> activeAni;
    protected float x;
    protected Rectangle hitbox;
    protected Array<Entity> hitEntites;
    protected float skillButtonWidth = 100, skillButtonHeight = 88;

    public Skill(Entity owner, int slot){
        this.owner = owner;
        this.slot = slot;
        cooldownTT = new Texture(Gdx.files.internal("atlas/skill/cooldown.png"));
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

    public void activate(){}

    public void updateHitBox(){}

    public void upgrade(){}

    public void draw(){
        this.statetime += Gdx.graphics.getDeltaTime();
        switch (this.state){
            case LOCKED:
                MyGdxGame.batch.draw(lockedTT, x, 0, skillButtonWidth, skillButtonHeight);
                break;
            case AVAILABLE:
                MyGdxGame.batch.draw(availableTT, x, 0, skillButtonWidth, skillButtonHeight);
                break;
            case ACTIVE:
                MyGdxGame.batch.draw(activeAni.getKeyFrame(statetime, true), x, 0, skillButtonWidth, skillButtonHeight);
                break;
            case COOLDOWN:
                MyGdxGame.batch.draw(availableTT, x, 0, skillButtonWidth, skillButtonHeight);
                cooldownRemain -= Gdx.graphics.getDeltaTime();
                if(cooldownRemain <= 0) this.state = SKILL_STATE.AVAILABLE;
                else{
                    float tmp = cooldownRemain / cooldown;
                    MyGdxGame.batch.draw(cooldownTT, x, 0, skillButtonWidth, skillButtonHeight * tmp);
                }
        }

        if(state == SKILL_STATE.ACTIVE) updateHitBox();
    }

    public void end(){}

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
