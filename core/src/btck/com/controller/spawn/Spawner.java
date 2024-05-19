package btck.com.controller.spawn;

import btck.com.GameManager;
import btck.com.model.constant.Constants;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.enemy.archer.Archer;
import btck.com.model.entity.enemy.knight.Knight;
import btck.com.model.entity.enemy.gladiator.Gladiator;
import lombok.Setter;

import java.util.Random;

public class Spawner {

    @Setter
    int maxEnemyAmount = 5;
    int maxEnemySpawnAtOnce;

    Random rand;

    public Spawner(int maxEnemyAmount, int maxEnemySpawnAtOnce){
        this.maxEnemyAmount = maxEnemyAmount;
        this.maxEnemySpawnAtOnce = maxEnemySpawnAtOnce;
        rand = new Random();
    }

    public void spawnEnemy(){
        int spawnAmount = rand.nextInt( maxEnemySpawnAtOnce) + 1;

        int current = GameManager.getInstance().getCurrentEnemyAmount();

        if(current + spawnAmount > maxEnemyAmount) spawnAmount = maxEnemyAmount - current;

        while(spawnAmount-- > 0){
            Enemy spawnEnemy = null;
            EnemyEnum enemyEnum = EnemyEnum.getRandom();
            switch (enemyEnum){
                case MUSHROOM :
                    spawnEnemy = new Knight();
                    break;
                case MAGE:
                    spawnEnemy = new Archer();
                    break;
                case GLADIATOR:
                    spawnEnemy = new Gladiator();
                    break;
            }

            float randomX = rand.nextInt((int) (Constants.screenWidth - spawnEnemy.width));
            float randomY = rand.nextInt((int) (Constants.screenHeight - spawnEnemy.height));
            spawnEnemy.setX(randomX);
            spawnEnemy.setY(randomY);
            GameManager.getInstance().addEnemy(spawnEnemy);
        }
    }

    public void spawnPlayer(){

    }

}
