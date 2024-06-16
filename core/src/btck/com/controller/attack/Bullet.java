package btck.com.controller.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;
import static java.lang.Math.tan;

@Getter
public class Bullet {

    float desX, desY;
    @Setter
    float speed;
    float xSpeed, ySpeed;
    Texture texture;
    Rectangle hitbox;
    float angle;
    int damage;

    public Bullet(Texture texture, int damage, float x, float y, float desX, float desY, int speed, float width, float height){
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
        this.hitbox = new Rectangle();
        this.hitbox.x = x;
        this.hitbox.y = y;
        this.hitbox.width = width;
        this.hitbox.height = height;

        if(abs(x - desX) <= 5){
            if(y <= desY) angle = 270;
            else angle = 90;
        }
        else if(abs(y - desY) <= 5){
            if(x <= desX) angle = 180;
            else angle = 0;
        }
        else{
            float tan = (y - desY) / (x - desX);
            angle = (float) Math.atan(tan);
            angle = angle * (float)(180 / Math.PI);

            if((angle > 0 && desY < y)
                    || (angle < 0 && desY > y)) angle += 180;
            else if(angle < 0) angle += 360;
        }

        float radiusAngle = (float) (angle * PI / 180);
        float tan = abs((float)tan(radiusAngle));
        xSpeed = (float) sqrt((speed * speed) / (1 + tan * tan));
        ySpeed = xSpeed * tan;
        if(angle > 90 && angle < 270) xSpeed *= -1;
        if(angle > 180 && angle < 360) ySpeed *= -1;

        System.out.println(angle);
    }

    public void move(){
        float xDist = xSpeed * Gdx.graphics.getDeltaTime();
        float yDist = ySpeed * Gdx.graphics.getDeltaTime();

        hitbox.x += xDist;
        hitbox.y += yDist;
    }
}
