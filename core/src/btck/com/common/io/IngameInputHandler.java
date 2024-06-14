package btck.com.common.io;

import btck.com.common.GameManager;
import btck.com.controller.SkillButtonManager;
import btck.com.model.state.GameState;
import btck.com.utils.DEBUG_MODE;
import btck.com.utils.Debugger;
import com.badlogic.gdx.InputProcessor;

public class IngameInputHandler implements InputProcessor {

    static public IngameInputHandler ingameInputHandler;

    static public IngameInputHandler getInstance(){
        if(ingameInputHandler == null) ingameInputHandler = new IngameInputHandler();
        return ingameInputHandler;
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
        character = Character.toLowerCase(character);
        switch (character){
            case 'q':
                SkillButtonManager.getInstance().active(1);
                break;
            case 'w':
                SkillButtonManager.getInstance().active(2);
                break;
            case 'e':
                SkillButtonManager.getInstance().active(3);
                break;
            case 'd':
                if(Debugger.debugMode == DEBUG_MODE.ON) Debugger.setDebugMode(DEBUG_MODE.OFF);
                else Debugger.setDebugMode(DEBUG_MODE.ON);
                break;
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
