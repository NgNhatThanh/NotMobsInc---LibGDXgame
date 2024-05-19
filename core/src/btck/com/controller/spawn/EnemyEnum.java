package btck.com.controller.spawn;

import java.util.Random;

public enum EnemyEnum {
    MUSHROOM, MAGE, GLADIATOR;

    private static final EnemyEnum[] enemies = values();

    private static final Random rnd = new Random();

    public static EnemyEnum getRandom(){
        return enemies[rnd.nextInt(enemies.length)];
    }
}
