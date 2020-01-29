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
    private static ProgressGroup instance;

    public static ProgressGroup getInstance() {
        if (instance == null) {
            instance = new ProgressGroup();
        }
        return instance;
    }

    private ProgressGroup() {
        this.addActor(ProgressControlElement.getInstance());
        this.addActor(ProgressElement.getInstance());
        this.addActor(SetElement.getInstance());
        this.addActor(SetControlElement.getInstance());
    }

    public void switchVisible() {
        setVisible(!isVisible());
    }

    public void update() {
        SetElement.getInstance().update();
        ProgressElement.getInstance().update();
    }
}
