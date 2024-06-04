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
    protected int damage;
    protected SKILL_STATE state = SKILL_STATE.LOCKED;
    protected Texture lockedTT;
    protected Texture availableTT;
    protected TextureAtlas atlas;
    protected Animation<TextureRegion> activeAni;
    protected float x;
    protected Rectangle hitbox;
    protected Array<Entity> hitEntites;

    public Skill(Entity owner, int slot){
        this.owner = owner;
        this.slot = slot;
        switch (slot){
            case 1 :
                x = 507;
                break;
            case 2 :
                x = 646;
                break;
            case 3 :
                x = 785;
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
                MyGdxGame.batch.draw(lockedTT, x, 0);
                break;
            case AVAILABLE:
                MyGdxGame.batch.draw(availableTT, x, 0);
                break;
            case ACTIVE:
                MyGdxGame.batch.draw(activeAni.getKeyFrame(statetime, true), x, 0);
                break;
            case COOLDOWN:

        }
    }

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
