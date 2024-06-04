package btck.com.common.io;

import btck.com.common.GameManager;
import btck.com.controller.attack.skill.SKILL_STATE;
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
                if(GameManager.getInstance().getCurrentPlayer().getSkills().first().getState() == SKILL_STATE.AVAILABLE){
                    GameManager.getInstance().getCurrentPlayer().getSkills().first().activate();
                }
                break;
            case 'w':
                if(GameManager.getInstance().getCurrentPlayer().getSkills().get(1).getState() == SKILL_STATE.AVAILABLE){
                    GameManager.getInstance().getCurrentPlayer().getSkills().get(1).activate();
                }
                break;
            case 'e':
                if(GameManager.getInstance().getCurrentPlayer().getSkills().get(2).getState() == SKILL_STATE.AVAILABLE){
                    GameManager.getInstance().getCurrentPlayer().getSkills().get(2).activate();
                }
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
