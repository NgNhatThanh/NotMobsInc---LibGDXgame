package btck.com.model.entity.player.ghost;

import btck.com.controller.attack.DEAL_DAMAGE_TIME;
import btck.com.common.sound.ConstantSound;
import btck.com.common.Constants;
import btck.com.model.entity.Player;
import btck.com.model.entity.player.Blinking;
import btck.com.view.effect.Upgrade;
import btck.com.view.screens.IngameScreen;
import btck.com.view.screens.ShockWave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static java.lang.Math.*;

public class Ghost extends Player {

    final int NORMAL_SPEED = 200;

    private float tan, deltaSP;

    public Ghost(){
        FRAME_DURATION = Constants.FRAME_DURATION[0];
        vulnerable = true;
        nextLevelExp = 5;
        expToLevelUp = 6;
        exist = true;

        this.frame = new Texture(Gdx.files.internal("atlas/player/ghost1/frame.png"));
        levelToUpgrade = 2;
        normalSpeed = NORMAL_SPEED;
        currentSpeed = normalSpeed;
        maxHealth = 10;
        currentHealth = maxHealth;

        hitbox = new Rectangle(x, y, width, height);

        atlas = new TextureAtlas(Constants.GHOST_1_ATLAS_PATH);
        animations = new Animation[4];

        animations[0] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_idle"));
        animations[1] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_run"));
        animations[2] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_attack"));
        animations[3] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_die"));

        width = animations[0].getKeyFrame(0).getRegionWidth();
        height = animations[0].getKeyFrame(0).getRegionHeight();

        attack = new DashAttack(animations[2], this, DEAL_DAMAGE_TIME.ONCE);

        skills = new Array<>();
        skills.add(new ImpactSkill(this));
        skills.add(new DeadlyBounceSkill(this));
        skills.add(new SlowTimeSkill(this));
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(!exist) return;
        statetime += Gdx.graphics.getDeltaTime();

        width = animations[animationIdx].getKeyFrame(statetime).getRegionWidth();
        height = animations[animationIdx].getKeyFrame(statetime).getRegionHeight();

        hitbox.x =  x - width / 2 + 10;
        hitbox.y = y;

        hitbox.width = width - 10;
        hitbox.height = height / 2;

        if(attacking && animations[animationIdx].isAnimationFinished(statetime)){
            statetime = 0;
            animationIdx = 1;
            attacking = false;
            attack.end();
        }

        if(attacking) attack.update(statetime);

        if(Blinking.blinking) Blinking.update();

        if(visible) spriteBatch.draw(animations[animationIdx].getKeyFrame(statetime, true), (flip ? width / 2 : -width / 2) + x, y, (flip ? -1 : 1) * width, height);

        if(!dead){
            if(!attacking){
                move(Gdx.input.getX(), Constants.SCREEN_HEIGHT - Gdx.input.getY());
            }
            else{
                move(attackX, attackY);
            }
        }

        if(!dead) update();
    }

    @Override
    public void update() {
        super.update();
        if(currentHealth > 0){
            while(currentExp >= expToLevelUp){
                expToLevelUp += nextLevelExp;
                nextLevelExp += 3;
                ++level;
                if(level == levelToUpgrade) upgrade();
            }
        }
    }

    public void attack(int x, int y) {
        if(!attacking) ConstantSound.getInstance().slash.play(ConstantSound.getInstance().getSoundVolume());

        if(!dead && !attacking){
            attackX = x; attackY = Constants.SCREEN_HEIGHT - y;
            animationIdx = 2;
            attacking = true;
            statetime = 0;
            attack.start();
        }
    }

    @Override
    public void move(float desX, float desY){
        if(abs(x - desX) < 5 && abs(y - desY) < 5) {
            if(!attacking) animationIdx = 0;
            angle = 0;
            return;
        }
        else if(!attacking) animationIdx = 1;

        flip = !(desX < x);

        deltaSP = currentSpeed * Gdx.graphics.getDeltaTime();

        if(abs(x - desX) < 5){
            if(desY > y) y += deltaSP;
            else y -= deltaSP;
            angle = 0;
            return;
        }

        if(abs(y - desY) < 5){
            if(desX > x) x += deltaSP;
            else x -= deltaSP;
            angle = 90;
            return;
        }

        tan = (y - desY) / (x - desX);

        angle = (float)Math.atan(tan);
        angle = angle * (float)(180 / Math.PI);

        if((angle > 0 && y > desY)
                || (angle < 0 && y < desY)) angle += 180;
        else if(angle < 0) angle += 360;

        xSpeed = (float) sqrt((currentSpeed * currentSpeed) / (1 + tan * tan));
        ySpeed = abs(xSpeed * tan);

        if(angle > 90 && angle < 270) xSpeed *= -1;
        if(angle > 180 && angle < 360) ySpeed *= -1;

        float xDist = xSpeed * Gdx.graphics.getDeltaTime();
        float yDist = ySpeed * Gdx.graphics.getDeltaTime();

        if(desX > x) x = Math.min(desX, x + xDist);
        else x = Math.max(desX, x + xDist);

        if(desY > y) y = Math.min(desY, y + yDist);
        else y = Math.max(desY, y + yDist);
    }

    @Override
    public void upgrade() {
        if(upgradeLevel == 4){
            levelToUpgrade = -1;
            return;
        }
        ++upgradeLevel;
        Sound sfx;
        switch (upgradeLevel){
            case 2:
                atlas = new TextureAtlas(Gdx.files.internal(Constants.GHOST_2_ATLAS_PATH));
                sfx = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/ghost2.mp3"));
                sfx.play(ConstantSound.constantSound.getSoundVolume());
                this.FRAME_DURATION = Constants.FRAME_DURATION[1];
                break;
            case 3:
                atlas = new TextureAtlas(Gdx.files.internal(Constants.GHOST_3_ATLAS_PATH));
                sfx = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/ghost3.mp3"));
                sfx.play(ConstantSound.constantSound.getSoundVolume());
                this.FRAME_DURATION = Constants.FRAME_DURATION[0];
                break;
            case 4:
                atlas = new TextureAtlas(Gdx.files.internal(Constants.GHOST_4_ATLAS_PATH));
                sfx = Gdx.audio.newSound(Gdx.files.internal("sound/sound ingame/ghost4.mp3"));
                sfx.play(ConstantSound.constantSound.getSoundVolume());
                this.FRAME_DURATION = Constants.FRAME_DURATION[0];
                ShockWave.getInstance().start(x, y);
                break;
        }

        this.frame = new Texture("atlas/player/ghost" + upgradeLevel + "/frame.png");

        animations[0] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_idle"));
        animations[1] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_run"));
        animations[2] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_attack"));
        animations[3] = new Animation<>(FRAME_DURATION, atlas.findRegions("spr_die"));

        width = animations[0].getKeyFrame(0).getRegionWidth();
        height = animations[0].getKeyFrame(0).getRegionHeight();

        attack.upgrade();
        for(int i = 0; i <= upgradeLevel - 2; ++i) skills.get(i).upgrade();

        levelToUpgrade += 2;
        IngameScreen.addTopEffect(new Upgrade(x, y));
    }
}