package btck.com.common.io;

import btck.com.GameManager;
import btck.com.model.constant.GameState;
import btck.com.utils.DEBUG_MODE;
import btck.com.utils.Debugger;
import com.badlogic.gdx.InputProcessor;

public class MouseHandler implements InputProcessor {

    static public MouseHandler mouseHandler;

    static public MouseHandler getInstance(){
        if(mouseHandler == null) mouseHandler = new MouseHandler();
        return mouseHandler;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(character == 'd' || character == 'D'){
            if(Debugger.debugMode == DEBUG_MODE.ON) Debugger.setDebugMode(DEBUG_MODE.OFF);
            else Debugger.setDebugMode(DEBUG_MODE.ON);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(GameManager.getInstance().gameState == GameState.INGAME) GameManager.getInstance().getCurrentPlayer().attack(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
