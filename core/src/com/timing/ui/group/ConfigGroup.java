package com.timing.ui.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.element.SimpleListElement;
import com.timing.ui.element.VerticalListElementList;
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
    private VerticalListElementList verticalListElementList;

    private Button back;
    private BoomBox boomBox;

    public static ConfigGroup getInstance() {
        if (instance == null) {
            instance = new ConfigGroup();
        }
        return instance;
    }

    public ConfigGroup() {
        this.setVisible(false);
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.boomBox = new BoomBox();
        this.back = new Button(skin);
        this.back.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                ListGroup.getInstance().switchVisible();
                ConfigGroup.getInstance().switchVisible();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        this.addActor(back);
        this.verticalListElementList = new VerticalListElementList();
        this.addActor(verticalListElementList);
    }

    public void switchVisible() {
        this.setVisible(!isVisible());
    }

    public void show(ProfileDAO profileDAO) {
        verticalListElementList.show(profileDAO);
    }

    public void showActive() {
        verticalListElementList.showActive();
    }
}
