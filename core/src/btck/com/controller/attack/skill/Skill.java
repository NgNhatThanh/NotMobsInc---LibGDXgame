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
    protected float cooldown;
    protected int damage;
    protected SKILL_STATE state = SKILL_STATE.LOCKED;
    protected Texture lockedTT;
    protected Texture availableTT;
    protected Rectangle hitbox;
    protected Array<Entity> hitEntites;

    public Skill(Entity owner){
        this.owner = owner;
    }

    public void activate(){}

    public void updateHitBox(){}

    public void upgrade(){}

    public void update(){
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
