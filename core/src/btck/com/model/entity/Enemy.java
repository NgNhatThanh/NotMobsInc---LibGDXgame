package btck.com.model.entity;

import btck.com.common.GameManager;
import btck.com.common.sound.ConstantSound;
import btck.com.view.effect.Bloodstain;
import btck.com.view.screens.IngameScreen;

public abstract class Enemy extends Entity{
    public int exp;
    public int attackRange;
    public int bonusHealth, bonusDamage;

    public Enemy(){
        bonusDamage = bonusHealth = GameManager.getInstance().getCurrentPlayer().getUpgradeLevel() - 1;
    }

    public void takeDamage(int damage){
        super.takeDamage(damage);
        if(damage > 0) ConstantSound.getInstance().playEnemyHitSound();
    }

    public void update(){
        super.update();
        if(isDead()) IngameScreen.addBottomEffect(new Bloodstain(this.x - this.width / 2, this.y - this.height / 2, 0));
    }
}