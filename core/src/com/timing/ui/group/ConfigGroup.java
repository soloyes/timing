package com.timing.ui.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.ui.mvc.profiles.GroupController;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.ui.mvc.profiles.VerticalGroupList;

/**
 * @author Shuttle on 16/01/20.
 */

public class ConfigGroup extends Group {
    private static ConfigGroup instance;
    private VerticalGroupList verticalGroupList;

    private GroupController groupController;

    public static ConfigGroup getInstance() {
        if (instance == null) {
            instance = new ConfigGroup();
        }
        return instance;
    }

    private ConfigGroup() {
        //MVC
        this.verticalGroupList = new VerticalGroupList();
        this.groupController = new GroupController(verticalGroupList, new Profiles());
        verticalGroupList.setController(groupController);
        groupController.update();
        //
        this.addActor(verticalGroupList);
    }

    public void switchVisible(){
        verticalGroupList.switchVisible();
    }
}
