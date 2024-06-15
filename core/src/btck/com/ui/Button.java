package btck.com.ui;

import btck.com.common.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {
    private int x, y, width, height;
    private String inactiveImg;
    private String activeImg;
    private String text;
    private boolean isHovered, isClicked;
    Texture textureInactive;
    Texture textureActive;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public Button(int x, int y, int width, int height, String inactiveImg, String activeImg){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        textureInactive = new Texture(Gdx.files.internal(inactiveImg));
        textureActive = new Texture(Gdx.files.internal(activeImg));
    }
    public void drawActive(SpriteBatch batch) {
        batch.draw(textureActive, x, y, width, height);
    }

    public void drawInactive(SpriteBatch batch) {
        batch.draw(textureInactive, x, y, width, height);
    }
    public void update(){
        int mouseX = Gdx.input.getX();
        int mouseY = Constants.SCREEN_HEIGHT - Gdx.input.getY();
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            isHovered = true;
        } else {
            isHovered = false;
        }
        if (isHovered && Gdx.input.isButtonJustPressed(0)) {
            isClicked = true;
        } else {
            isClicked = false;
        }
    }
    public void draw(SpriteBatch batch){
        if(isHovered){
            batch.draw(textureActive,x , y, width, height);
        }
        else{
            batch.draw(textureInactive,x , y, width, height);
        }

    }

    public void dispose(){
        textureActive.dispose();
        textureInactive.dispose();
    }

}
