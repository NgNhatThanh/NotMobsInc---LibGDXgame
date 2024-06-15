package btck.com.ui;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

public class Button {
    private int x, y, width, height;
    @Getter
    @Setter
    private String text;
    private boolean isHovered, isClicked;
    Texture textureInactive;
    Texture textureActive;

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
        isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        isClicked = isHovered && Gdx.input.isButtonJustPressed(0);
    }
    public void draw(){
        if(isHovered){
            MyGdxGame.batch.draw(textureActive,x , y, width, height);
        }
        else{
            MyGdxGame.batch.draw(textureInactive,x , y, width, height);
        }

    }

    public void dispose(){
        textureActive.dispose();
        textureInactive.dispose();
    }

}
