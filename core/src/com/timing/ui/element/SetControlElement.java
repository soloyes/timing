package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ListGroup;
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
    private Button edit;
    private Button loop;

    private BoomBox boomBox;
    @Getter
    private boolean looped;

    private SetControlElement() {
        this.boomBox = new BoomBox();
        this.create = new ImageButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_CREATE))
        );
        this.create.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE - 3 * PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        create.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        create.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ListGroup.getInstance().switchVisible();
                ProgressGroup.getInstance().switchVisible();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        this.edit = new ImageButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_EDIT))
        );
        this.edit.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        edit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ConfigGroup.getInstance().showActive();
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.loop = new ImageButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_LOOP))
        );
        this.loop.setBounds(Rules.WORLD_WIDTH / 2 + PaintConstants.BUTTON_SPACE + PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        loop.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                looped = !looped;
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.addActor(create);
        this.addActor(edit);
        this.addActor(loop);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        create.draw(batch, parentAlpha);
        edit.draw(batch, parentAlpha);
        loop.draw(batch, parentAlpha);

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}