package btck.com.model.entity;

import btck.com.common.io.sound.ConstantSound;

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
}
