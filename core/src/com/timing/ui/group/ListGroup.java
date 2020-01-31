package com.timing.ui.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.UIButton;
import com.timing.ui.mvc.profiles.GroupController;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.ui.mvc.profiles.VerticalGroupList;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

/**
 * @author Shuttle on 16/01/20.
 */

public class ListGroup extends Group {
    private static ListGroup instance;
    private VerticalGroupList verticalGroupList;
    private GroupController groupController;

    private Button back;
    private BoomBox boomBox;

    public static ListGroup getInstance() {
        if (instance == null) {
            instance = new ListGroup();
        }
        return instance;
    }

    private ListGroup() {
        this.setVisible(false);
        this.boomBox = new BoomBox();
        this.back = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_BACK))
        );
        this.back.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        back.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ListGroup.getInstance().switchVisible();
                ProgressGroup.getInstance().switchVisible();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        //MVC
        this.verticalGroupList = new VerticalGroupList();
        this.groupController = new GroupController(verticalGroupList, new Profiles());
        verticalGroupList.setController(groupController);
        groupController.update();
        //
        this.addActor(verticalGroupList);
        this.addActor(back);
    }

    public void switchVisible() {
        verticalGroupList.switchVisible();
        this.setVisible(!isVisible());
    }

    public void dispose() {
        groupController.dispose();
    }

    public void update() {
        groupController.update();
    }
}
