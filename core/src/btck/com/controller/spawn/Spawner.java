package btck.com.controller.spawn;

import btck.com.model.entity.Enemy;
import btck.com.model.entity.enemy.mage.Mage;
import btck.com.model.entity.enemy.mushroom.Mushroom;
import lombok.Setter;

public class Spawner {

    @Setter
    int maxEnemyAmount = 5;

    public Spawner(int maxEnemyAmount){
        this.maxEnemyAmount = maxEnemyAmount;
    }

    public void spawnEnemy(){

    }

    public void spawnPlayer(){

    }

}
