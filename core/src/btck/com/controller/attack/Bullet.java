package btck.com.controller.attack;

import btck.com.common.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

@Getter
public class Bullet {

    float desX, desY;
    @Setter
    float speed;
    Texture texture;
    Rectangle hitbox;
    float rotation;
    boolean flip = true;
    int damage;
    float a, b, x1, y1, deltaSP;

    public Bullet(Texture texture, int damage, float x, float y, float desX, float desY, int speed, float width, float height){
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
        this.hitbox = new Rectangle();
        this.hitbox.x = x;
        this.hitbox.y = y;
        this.hitbox.width = width;
        this.hitbox.height = height;
        if(desX < x) flip = false;

        if(desX > x) this.desX = Constants.SCREEN_WIDTH + 10;
        else this.desX = -10;

        a = (y - desY) / (x - desX);
        b = y - a * x;

        rotation = (float) Math.atan(a);
        rotation = rotation * (float)(180 / Math.PI);

        this.desY = a * this.desX + b;
    }

    public void move(){
        deltaSP = speed * Gdx.graphics.getDeltaTime();

        if(abs(hitbox.x - desX) < 5){
            if(desY > hitbox.y) hitbox.y += deltaSP;
            else hitbox.y -= deltaSP;
            return;
        }

        if(abs(hitbox.y - desY) < 5){
            if(desX > hitbox.x) hitbox.x += deltaSP;
            else hitbox.x -= deltaSP;
            return;
        }

        x1 = hitbox.x;
        y1 = hitbox.y;
        while(sqrt((x1 - hitbox.x) * (x1 - hitbox.x) + (y1 - hitbox.y) * (y1 - hitbox.y)) < deltaSP){
            if(hitbox.x < desX) x1 += deltaSP / (50 * abs(a));
            else x1 -= deltaSP / (50 * abs(a));
            y1 = a * x1 + b;
        }

        hitbox.x = x1;
        hitbox.y = y1;
    }

}
