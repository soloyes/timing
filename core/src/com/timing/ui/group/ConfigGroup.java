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
import com.timing.ui.element.ProgressControlElement;
import com.timing.ui.element.SimpleListElement;
import com.timing.ui.element.VerticalElementList;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shuttle on 17/01/20.
 */

public class ConfigGroup extends Group {
    private static ConfigGroup instance;
    @Setter
    @Getter
    private SimpleListElement simpleListElement;
    private VerticalElementList verticalElementList;

    private Button back;
    private Button home;
    private BoomBox boomBox;

    public static ConfigGroup getInstance() {
        if (instance == null) {
            instance = new ConfigGroup();
        }
        return instance;
    }

    public ConfigGroup() {
        this.setVisible(false);
        this.boomBox = new BoomBox();
        this.back = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_BACK))
        );
        this.home = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_HOME))
        );

        this.back.setBounds(Rules.WORLD_WIDTH / 2 - 2 * PaintConstants.BUTTON_SPACE, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        this.home.setBounds(Rules.WORLD_WIDTH / 2 + PaintConstants.BUTTON_SPACE / 2, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        back.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (back.isOver()) {
                    ListGroup.getInstance().switchVisible();
                    ConfigGroup.getInstance().switchVisible();
                    ProgressControlElement.getInstance().reset();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        home.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (home.isOver()) {
                    ProgressGroup.getInstance().switchVisible();
                    ConfigGroup.getInstance().switchVisible();
                    ProgressControlElement.getInstance().reset();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.verticalElementList = new VerticalElementList();
        this.addActor(verticalElementList);

        this.addActor(back);
        this.addActor(home);
    }

    public void switchVisible() {
        this.setVisible(!isVisible());
    }

    public void show(ProfileDAO profileDAO) {
        verticalElementList.show(profileDAO);
    }

    public void showActive() {
        verticalElementList.showActive();
    }
}
