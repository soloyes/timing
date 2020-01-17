package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ProgressGroup;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class SetControlElement extends Group {
    @Getter
    private static SetControlElement instance = new SetControlElement();

    private Button create;
    private Button modify;
    private Button loop;

    private BoomBox boomBox;

    private SetControlElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.boomBox = new BoomBox();
        this.create = new Button(skin);
        this.create.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE - 3 * PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        create.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        create.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ConfigGroup.getInstance().switchVisible();
                ProgressGroup.getInstance().switchVisible();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        this.modify = new Button(skin);
        this.modify.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        modify.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.loop = new Button(skin);
        this.loop.setBounds(Rules.WORLD_WIDTH / 2 + PaintConstants.BUTTON_SPACE + PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        loop.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.addActor(create);
        this.addActor(modify);
        this.addActor(loop);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        create.draw(batch, parentAlpha);
        modify.draw(batch, parentAlpha);
        loop.draw(batch, parentAlpha);

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
