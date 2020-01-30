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
    private ComplexListElement complexListElement;

    private SetElement() {
        this.profiles = new Profiles();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO == null) {
            complexListElement = null;
        } else {
            complexListElement = new ComplexListElement(profileDAO);
        }
        if (complexListElement != null) {
            this.addActor(complexListElement);
        }

        this.setPosition((Rules.WORLD_WIDTH / 4) + PaintConstants.LIST_ELEMENT_PAD / 2, PaintConstants.SET_ELEMENT_HEIGHT);
    }

    public void update() {
        this.clear();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO != null) {
            complexListElement = new ComplexListElement(profileDAO);
            this.addActor(complexListElement);
        }
    }
}