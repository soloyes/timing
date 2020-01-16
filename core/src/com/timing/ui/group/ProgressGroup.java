package com.timing.ui.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.ui.element.ProgressControlElement;
import com.timing.ui.element.ProgressElement;
import com.timing.ui.element.SetControlElement;
import com.timing.ui.element.SetElement;

/**
 * @author Shuttle on 16/01/20.
 */

public class ProgressGroup extends Group {
    public ProgressGroup() {
        this.addActor(ProgressControlElement.getInstance());
        this.addActor(ProgressElement.getInstance());
        this.addActor(SetElement.getInstance());
        this.addActor(SetControlElement.getInstance());
    }
}
