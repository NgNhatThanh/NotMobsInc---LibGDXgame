package btck.com.view.screens;

import btck.com.MyGdxGame;
import btck.com.common.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class ShockWave {

    private final FrameBuffer fbo;
    private String vertexShader ;
    private String fragmentShader ;
    private final ShaderProgram shaderProgram;
    private float time;
    private float duration = 0.9f;

    public boolean enabled;

    static private ShockWave shockWave;

    Vector2 v = new Vector2();

    static public ShockWave getInstance(){
        if(shockWave==null){
            shockWave=new ShockWave();
        }
        return shockWave;
    }

    private ShockWave(){
        enabled = false;
        time = 0;
        vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("shaders/fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        ShaderProgram.pedantic = false;
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
    }

    public void start(float posX,float posY){
        v = new Vector2(posX, posY);  // shockwave center
        v.x = v.x / Constants.SCREEN_WIDTH;
        v.y = v.y / Constants.SCREEN_HEIGHT;
        enabled = true;
        time = 0;
    }

    public void draw() {
        update();
        
        MyGdxGame.batch.end();
        MyGdxGame.batch.flush();
        fbo.begin();
        MyGdxGame.batch.begin();
        MyGdxGame.batch.draw(IngameScreen.getMap(), 0, 0);
        MyGdxGame.batch.end();
        MyGdxGame.batch.flush();
        fbo.end();
        MyGdxGame.batch.begin();
        MyGdxGame.batch.setShader(shaderProgram);
        shaderProgram.setUniformf("time", time);
        shaderProgram.setUniformf("center", v);
        Texture texture = fbo.getColorBufferTexture();
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.flip(false, true);
        MyGdxGame.batch.draw(textureRegion, 0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGdxGame.batch.setShader(null);
    }
    
    public void update(){
        time += Gdx.graphics.getDeltaTime();
        if(time > duration) enabled = false;
    }
}