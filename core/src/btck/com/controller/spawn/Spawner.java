package btck.com.controller.spawn;

import btck.com.common.GameManager;
import btck.com.common.Constants;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.enemy.archer.Archer;
import btck.com.model.entity.enemy.knight.Knight;
import btck.com.model.entity.enemy.gladiator.Gladiator;
import lombok.Setter;

import java.util.Random;

public class Spawner {

    @Setter
    int maxEnemyAmount;
    int maxEnemySpawnAtOnce;
    int bonusSpawn;

    int playerSpawnX = Constants.SCREEN_WIDTH / 2 - GameManager.getInstance().getCurrentPlayer().width / 2;
    int playerSpawnY = Constants.SCREEN_HEIGHT / 2 - GameManager.getInstance().getCurrentPlayer().height / 2;

    Random rand;

    public Spawner(int maxEnemyAmount, int maxEnemySpawnAtOnce){
        this.maxEnemyAmount = maxEnemyAmount;
        this.maxEnemySpawnAtOnce = maxEnemySpawnAtOnce;
        rand = new Random();
    }

    public void spawnEnemy(){
        bonusSpawn = GameManager.getInstance().getCurrentPlayer().getLevel() / 5;

        int spawnAmount = rand.nextInt( maxEnemySpawnAtOnce) + 1 + bonusSpawn;

        int current = GameManager.getInstance().getCurrentEnemyAmount();

        if(current + spawnAmount > maxEnemyAmount + bonusSpawn) spawnAmount = maxEnemyAmount + bonusSpawn - current;

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

            float randomX = rand.nextInt(Constants.SCREEN_WIDTH - 100) + 50;
            float randomY = rand.nextInt(Constants.SCREEN_HEIGHT - 200) + 100;
            spawnEnemy.setX(randomX);
            spawnEnemy.setY(randomY);
            GameManager.getInstance().addEnemy(spawnEnemy);
        }
    }

    public void spawnPlayer(){
        GameManager.getInstance().getCurrentPlayer().setX(playerSpawnX);
        GameManager.getInstance().getCurrentPlayer().setY(playerSpawnY);
    }

}
