package btck.com.controller.spawn;

import btck.com.model.constant.GameConstant;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.enemy.mage.Mage;
import btck.com.model.entity.enemy.mushroom.Mushroom;
import lombok.Setter;
import screens.IngameScreen;

import java.util.Random;

public class Spawner {

    @Setter
    int maxEnemyAmount = 5;
    int maxEnemySpawnAtOnce;

    IngameScreen ingameScreen;

    Random rand;

    public Spawner(IngameScreen ingameScreen, int maxEnemyAmount, int maxEnemySpawnAtOnce){
        this.maxEnemyAmount = maxEnemyAmount;
        this.ingameScreen = ingameScreen;
        this.maxEnemySpawnAtOnce = maxEnemySpawnAtOnce;
        rand = new Random();
    }

    public void spawnEnemy(){
        int spawnAmount = rand.nextInt( maxEnemySpawnAtOnce) + 1;

        int current = ingameScreen.getCurrentEnemyAmount();

        if(current + spawnAmount > maxEnemyAmount) spawnAmount = maxEnemyAmount - current;

        while(spawnAmount-- > 0){
            Enemy spawnEnemy = null;
            EnemyEnum enemyEnum = EnemyEnum.getRandom();
            switch (enemyEnum){
                case MUSHROOM :
                    spawnEnemy = new Mushroom();
                    break;
                case MAGE:
                    spawnEnemy = new Mage();
                    break;
            }

            float randomX = rand.nextInt((int) (GameConstant.screenWidth - spawnEnemy.width));
            float randomY = rand.nextInt((int) (GameConstant.screenHeight - spawnEnemy.height));
            spawnEnemy.setX(randomX);
            spawnEnemy.setY(randomY);
            ingameScreen.addEnemy(spawnEnemy);
        }
    }

    public void spawnPlayer(){

    }

}
