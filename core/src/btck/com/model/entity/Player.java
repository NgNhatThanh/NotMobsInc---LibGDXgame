package btck.com.model.entity;

import btck.com.GameManager;
import btck.com.common.io.Constants;
import btck.com.common.io.sound.ConstantSound;
import btck.com.model.constant.PlayerState;
import btck.com.model.entity.player.Blinking;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

import java.nio.channels.spi.SelectorProvider;
@Getter
@Setter
public abstract class Player extends Entity{

    public int level;
    public int currentExp;
    public int expToLevelUp;
    public int nextLevelExp;

    public void takeDamage(int damage){
        super.takeDamage(damage);
        if(damage > 0) ConstantSound.getInstance().playPlayerHitSFX();
        if(!dead) Blinking.blink();
    }
    public void drawStill(SpriteBatch batch) {
        batch.draw(GameManager.getInstance().getCurrentPlayer().sampleTexture, x, y, width, height);
    }
}