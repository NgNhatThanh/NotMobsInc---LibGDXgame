package btck.com.model.entity;

import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.skill.Skill;
import btck.com.model.entity.player.Blinking;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Player extends Entity{

    public int level;
    public int currentExp;
    public int expToLevelUp;
    public int nextLevelExp;
    protected int upgradeLevel = 1;
    protected int levelToUpgrade;
    public Array<Skill> skills;

    public void takeDamage(int damage){
        super.takeDamage(damage);
        if(damage > 0) ConstantSound.getInstance().playPlayerHitSFX();
        if(!dead) Blinking.blink();
    }

    public abstract void upgrade();

}
