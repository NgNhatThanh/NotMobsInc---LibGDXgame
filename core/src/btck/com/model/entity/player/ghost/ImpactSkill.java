package btck.com.model.entity.player.ghost;

import btck.com.common.Constants;
import btck.com.common.GameManager;
import btck.com.common.io.IngameInputHandler;
import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import btck.com.crowd_control.SlowMo;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.Player;
import btck.com.model.entity.player.Blinking;
import btck.com.view.effect.AirStrike;
import btck.com.view.effect.AirStrikeCall;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import btck.com.view.screens.IngameScreen;
import btck.com.view.screens.eff.Rumble;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class ImpactSkill extends Skill {

    public ImpactSkill(Entity owner) {
        super(owner);
        this.cooldown = 5;
        this.lockedTT = new Texture(Gdx.files.internal("atlas/skill/impact/locked.png"));
        this.availableTT = new Texture(Gdx.files.internal("atlas/skill/impact/available.png"));
        this.FRAME_DURATION = Constants.FRAME_DURATION[0];
        this.hitbox = new Rectangle(0, 0, 250, 250);
    }

    @Override
    public void activate() {
        Gdx.input.setInputProcessor(this);
        Blinking.stop();
        this.state = SKILL_STATE.ACTIVE;
        this.statetime = 0;
        owner.setVisible(false);
        owner.setVulnerable(false);
        owner.setAttacking(false);
        owner.setCurrentSpeed(0);
        SlowMo.activateAll();
        IngameScreen.addTopEffect(new AirStrikeCall(owner.getX(), owner.getY(), false));
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        owner.setX(i);
        owner.setY(Constants.SCREEN_HEIGHT - i1);
        owner.setVulnerable(true);
        owner.setVisible(true);
        owner.setCurrentSpeed(owner.getNormalSpeed());
        SlowMo.deactivateAll();
        IngameScreen.addTopEffect(new AirStrike(i, Constants.SCREEN_HEIGHT - i1));

        hitbox.x = owner.getX() - hitbox.width / 2;
        hitbox.y = owner.getY() - hitbox.height / 2;
        for(Enemy enemy : GameManager.getInstance().getEnemies()){
            if(enemy.isVulnerable() && enemy.getHitbox().overlaps(this.hitbox)){
                Rumble.rumble();
                IngameScreen.addTopEffect(new Slice(enemy.getX(), enemy.getY(), owner.getAngle(), owner.getHeight(), SLICE_COLOR.RED));
                enemy.takeDamage(this.damage);
                if(enemy.isDead()) ((Player) owner).currentExp += enemy.exp;
            }
        }
        end();
        return false;
    }

    public void updateHitBox(){
        this.hitbox.x = Gdx.input.getX() - this.hitbox.width / 2;
        this.hitbox.y = Constants.SCREEN_HEIGHT - Gdx.input.getY() - this.hitbox.height / 2;
    }

    public void upgrade(){
        if(this.state == SKILL_STATE.LOCKED) this.state = SKILL_STATE.AVAILABLE;
        else{
            this.cooldown -= 0.5f;
            hitbox.width += 10;
        }
        this.damage = owner.getAttack().getDamage() * 2;
    }

    public void end(){
        Gdx.input.setInputProcessor(IngameInputHandler.getInstance());
        ConstantSound.getInstance().setBgmVolume(ConstantSound.getInstance().getBgmVolume() * 3);
        this.state = SKILL_STATE.COOLDOWN;
    }
}
