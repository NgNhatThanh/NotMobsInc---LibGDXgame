package btck.com.controller.attack;

import btck.com.model.constant.GameConstant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bullet {

    float desX, desY;
    int speed;

    @Getter
    Rectangle hitbox;

    float a, b, x1, y1, deltaSP;

    public Bullet(float x, float y, float desX, float desY, int speed, float width, float height){
        this.speed = speed;
        this.hitbox = new Rectangle();
        this.hitbox.x = x;
        this.hitbox.y = y;
        this.hitbox.width = width;
        this.hitbox.height = height;

        if(desX > x) this.desX = GameConstant.screenWidth + 10;
        else this.desX = -10;

        a = (y - desY) / (x - desX);
        b = y - a * x;

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
