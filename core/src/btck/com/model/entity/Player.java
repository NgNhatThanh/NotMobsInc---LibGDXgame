package btck.com.model.entity;

import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.skill.Skill;
import btck.com.crowd_control.SlowMo;
import btck.com.model.entity.player.Blinking;
import btck.com.view.effect.AirStrikeCall;
import btck.com.view.screens.IngameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    protected Texture frame;

    public void takeDamage(int damage){
        super.takeDamage(damage);
        if(damage > 0) ConstantSound.getInstance().playPlayerHitSFX();
        if(!dead) Blinking.blink();
    }

    public abstract void upgrade();

    public void update(){
        super.update();
        if(dead){
            animationIdx = 0;
            visible = false;
            Gdx.input.setInputProcessor(null);
            IngameScreen.addTopEffect(new AirStrikeCall(x, y, true));
            SlowMo.activateAll();
            SlowMo.activateEntity(this);
        }
        for(Skill skill : skills) skill.update();
    }

}
