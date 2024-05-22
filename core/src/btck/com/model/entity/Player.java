package btck.com.model.entity;

import btck.com.model.constant.PlayerState;
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

}
