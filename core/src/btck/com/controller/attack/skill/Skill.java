package btck.com.controller.attack.skill;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Skill implements InputProcessor {

    int slot;

    SKILL_STATE state;

    Texture lockedTT;

    Texture availableTT;

    Animation<TextureRegion> active;

}
