package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.skill.Skill;
import btck.com.model.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ShieldSkill extends Skill {

    public ShieldSkill(Entity owner, int slot) {
        super(owner, slot);
        this.lockedTT = new Texture(Gdx.files.internal("atlas/skill/shield/locked.png"));
    }

}
