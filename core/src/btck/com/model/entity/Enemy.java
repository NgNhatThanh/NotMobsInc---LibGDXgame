package btck.com.model.entity;

import btck.com.common.io.sound.ConstantSound;
import btck.com.view.effect.Bloodstain;
import screens.IngameScreen;

public abstract class Enemy extends Entity{
    public int exp;
    public int attackRange;

    public int getExp() {
        return exp;
    }

    public void takeDamage(int damage){
        super.takeDamage(damage);
        ConstantSound.getInstance().playEnemyHitSound();
    }

    public void update(){
        super.update();
        if(isDead()) IngameScreen.addBottomEffect(new Bloodstain(this.x - this.width / 2, this.y - this.height / 2, 0));
    }
}
