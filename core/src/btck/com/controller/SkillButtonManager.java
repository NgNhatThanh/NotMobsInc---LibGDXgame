package btck.com.controller;

import btck.com.common.GameManager;
import btck.com.view.hud.SkillButton;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SkillButtonManager extends Group {

    static SkillButtonManager manager;

    public static SkillButtonManager getInstance(){
        if(manager == null) manager = new SkillButtonManager();
        return manager;
    }

    SkillButton qBtn, wBtn, eBtn;

    public void setButtons(int slot1, int slot2, int slot3){
        this.clear();

        qBtn = new SkillButton(GameManager.getInstance().getCurrentPlayer().skills.get(slot1 - 1), 1);
        wBtn = new SkillButton(GameManager.getInstance().getCurrentPlayer().skills.get(slot2 - 1), 2);
        eBtn = new SkillButton(GameManager.getInstance().getCurrentPlayer().skills.get(slot3 - 1), 3);

        this.addActor(qBtn);
        this.addActor(wBtn);
        this.addActor(eBtn);
    }

    public void active(int slot){
        switch (slot){
            case 1:
                qBtn.activate();
                break;
            case 2:
                wBtn.activate();
                break;
            case 3:
                eBtn.activate();
        }
    }
}
