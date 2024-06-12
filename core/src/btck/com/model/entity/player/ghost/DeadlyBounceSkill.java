package btck.com.model.entity.player.ghost;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import btck.com.common.GameManager;
import btck.com.common.io.IngameInputHandler;
import btck.com.common.sound.ConstantSound;
import btck.com.controller.attack.skill.SKILL_STATE;
import btck.com.controller.attack.skill.Skill;
import btck.com.crowd_control.SlowMo;
import btck.com.model.entity.Enemy;
import btck.com.model.entity.Entity;
import btck.com.model.entity.Player;
import btck.com.model.entity.player.Blinking;
import btck.com.view.effect.AirStrikeCall;
import btck.com.view.effect.SLICE_COLOR;
import btck.com.view.effect.Slice;
import btck.com.view.screens.IngameScreen;
import btck.com.view.screens.Rumble;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ConcurrentSkipListMap;

import static java.lang.Math.*;

public class DeadlyBounceSkill extends Skill {

    Texture orb, orbCall, direct;

    boolean calling;

    float orbSpeed = 1500, xSpeed, ySpeed;

    float orbX, orbY, orbCenterX, orbCenterY;

    float orbSize;

    float directAngle;

    float rollTime = 4, curRollTime;

    Array<Enemy> hitEntities;

    public DeadlyBounceSkill(Entity owner, int slot) {
        super(owner, slot);
        this.orbSize = owner.getHeight();   //
        this.state = SKILL_STATE.AVAILABLE;  //
        this.cooldown = 8;
        this.lockedTT = new Texture(Gdx.files.internal("atlas/skill/deadlybounce/locked.png"));
        this.availableTT = new Texture(Gdx.files.internal("atlas/skill/deadlybounce/available.png"));
        this.FRAME_DURATION = Constants.FRAME_DURATION[0];
        this.atlas = new TextureAtlas(Gdx.files.internal("atlas/skill/deadlybounce/active.atlas"));
        this.activeAni = new Animation<>(FRAME_DURATION, atlas.findRegions("active"));

        this.hitEntities = new Array<>();
        this.hitbox = new Rectangle();
        orb = new Texture(Gdx.files.internal("atlas/skill/deadlybounce/orb.png"));
        orbCall = new Texture(Gdx.files.internal("atlas/skill/deadlybounce/orb-call.png"));
        direct = new Texture(Gdx.files.internal("atlas/skill/deadlybounce/direct.png"));
    }

    public void activate(){
        Gdx.input.setInputProcessor(this);
        Blinking.stop();
        this.state = SKILL_STATE.ACTIVE;
        this.curRollTime = 0;
        this.statetime = 0;
        this.calling = true;
        this.orbX = owner.getX() - orbSize / 2;
        this.orbY = owner.getY();
        this.orbCenterX = orbX + orbSize / 2;
        this.orbCenterY = orbY + orbSize / 2;
        owner.setVisible(false);
        owner.setVulnerable(false);
        SlowMo.activateAll();
        IngameScreen.addTopEffect(new AirStrikeCall(owner.getX(), owner.getY()));

        this.hitbox.width = orbSize;  //
        this.hitbox.height = orbSize;  //
    }

    public boolean touchDown(int x1, int y1, int x2, int y2){
        if(calling){
            SlowMo.deactivateAll();
            this.calling = false;
            this.hitbox.x = orbX;
            this.hitbox.y = orbY;

            float radiusAngle = (float) (directAngle * PI / 180);
            float tan = abs((float)tan(radiusAngle));
            xSpeed = (float) sqrt((orbSpeed * orbSpeed) / (1 + tan * tan));
            ySpeed = xSpeed * tan;
            if(directAngle > 90 && directAngle < 270) xSpeed *= -1;
            if(directAngle > 180 && directAngle < 360) ySpeed *= -1;
        }

        return false;
    }

    public boolean mouseMoved(int x, int y){
        if(calling){
            y = Constants.SCREEN_HEIGHT - y;
            float tan;
            if(abs(x - orbCenterX) <= 5){
                if(y <= orbCenterY) directAngle = 270;
                else directAngle = 90;
            }
            else if(abs(y - orbCenterY) <= 5){
                if(x <= orbCenterX) directAngle = 180;
                else directAngle = 0;
            }
            else{
                tan = (y - orbCenterY) / (x - orbCenterX);
                directAngle = (float) Math.atan(tan);
                directAngle = directAngle * (float)(180 / Math.PI);

                if((directAngle > 0 && y < orbCenterY)
                    || (directAngle < 0 && y > orbCenterY)) directAngle += 180;
                else if(directAngle < 0) directAngle += 360;
            }
        }
        return false;
    }

    public void draw(){
        super.draw();
        if(state == SKILL_STATE.ACTIVE){
            if(calling){
                MyGdxGame.batch.draw(orbCall, orbX - orbSize / 2, orbY - orbSize / 2, orbSize * 2, orbSize * 1.7f);
                MyGdxGame.batch.draw(direct,
                        orbCenterX,
                        orbCenterY,
                        0,
                        (float) direct.getHeight() / 2,
                        direct.getWidth(),
                        direct.getHeight(),
                        1, 1,
                        directAngle,
                        0, 0,
                        direct.getWidth(),
                        direct.getHeight(),
                        false,
                        false);
            }
            else{
                MyGdxGame.batch.draw(orb, orbX, orbY, orbSize, orbSize);
            }
        }
    }

    public void updateHitBox(){
        if(calling) return;
        curRollTime += Gdx.graphics.getDeltaTime();
        if(orbX <= 0 || orbX + hitbox.width >= Constants.SCREEN_WIDTH) xSpeed *= -1;
        if(orbY <= 0 || orbY + hitbox.height >= Constants.SCREEN_HEIGHT) ySpeed *= -1;

        float xDist = xSpeed * Gdx.graphics.getDeltaTime();
        float yDist = ySpeed * Gdx.graphics.getDeltaTime();

        orbCenterX += xDist;
        orbCenterY += yDist;

        orbX = orbCenterX - orbSize / 2;
        orbY = orbCenterY - orbSize / 2;

        hitbox.x = orbX;
        hitbox.y = orbY;

        owner.setX(orbCenterX);
        owner.setY(orbCenterY);

        for(Enemy enemy : GameManager.getInstance().getEnemies()){
            if(enemy.isVulnerable() && enemy.getHitbox().overlaps(this.hitbox)){
                if(hitEntities.contains(enemy, false)) continue;
                hitEntities.add(enemy);
                Rumble.rumble();
                IngameScreen.addTopEffect(new Slice(enemy.getX(), enemy.getY(), owner.getAngle(), owner.getHeight(), SLICE_COLOR.RED));
                enemy.takeDamage(this.damage);
                if(enemy.isDead()) ((Player) owner).currentExp += enemy.exp;
            }
            else hitEntities.removeValue(enemy,false);
        }
        if(curRollTime >= rollTime) end();
    }

    public void upgrade(){
        if(this.state == SKILL_STATE.LOCKED) this.state = SKILL_STATE.AVAILABLE;
        else{
            this.cooldown--;
            this.rollTime++;
            this.orbSpeed += 200;
        }
        this.orbSize = owner.getHeight() ;
        this.hitbox.width = orbSize;
        this.hitbox.height = orbSize;
        this.damage = owner.getAttack().getDamage() * 2;
    }

    public void end(){
        this.state = SKILL_STATE.COOLDOWN;
        cooldownRemain = cooldown;
        owner.setVisible(true);
        owner.setVulnerable(true);
        Gdx.input.setInputProcessor(IngameInputHandler.getInstance());
    }
}