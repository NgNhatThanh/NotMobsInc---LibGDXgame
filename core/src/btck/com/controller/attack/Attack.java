package btck.com.controller.attack;

import btck.com.model.entity.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Attack {

    Array<Entity> hitEntities;

    Animation<TextureRegion> animation;

    public Attack(Animation<TextureRegion> animation){
        this.animation = animation;

    }

}
