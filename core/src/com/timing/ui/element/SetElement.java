package com.timing.ui.element;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class SetElement extends Group {
    @Getter
    private static SetElement instance = new SetElement();
    private Profiles profiles;
    private ListElement listElement;

    private SetElement() {
        this.profiles = new Profiles();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO == null) {
            listElement = null;
        } else {
            listElement = new ListElement(profileDAO);
        }
        if (listElement != null) {
            this.addActor(listElement);
        }

        this.setPosition(Rules.WORLD_WIDTH / 4, PaintConstants.SET_ELEMENT_HEIGHT);
    }

    public void update() {
        this.clear();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO != null){
            listElement = new ListElement(profileDAO);
            this.addActor(listElement);
        }
    }
}