package com.timing.ui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.UIButton;
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
    private Button loop;

    private BoomBox boomBox;
    @Getter
    private boolean looped;

    private SetControlElement() {
        this.boomBox = new BoomBox();
        this.create = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_CREATE)),
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_EDIT))

        );
        this.create.setBounds((Rules.WORLD_WIDTH / 4) + PaintConstants.LIST_ELEMENT_PAD / 2 - PaintConstants.BUTTON_HEIGHT - PaintConstants.LIST_ELEMENT_PAD, PaintConstants.SET_ELEMENT_HEIGHT - (PaintConstants.BUTTON_HEIGHT - PaintConstants.SET_HEIGHT), PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        create.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (create.isOver()) {
                    ListGroup.getInstance().switchVisible();
                    ProgressGroup.getInstance().switchVisible();
                    ProgressElement.getInstance().reset();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        create.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.loop = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_LOOP))
        );
        this.loop.setBounds(3 * (Rules.WORLD_WIDTH / 4) + PaintConstants.LIST_ELEMENT_PAD, PaintConstants.SET_ELEMENT_HEIGHT - (PaintConstants.BUTTON_HEIGHT - PaintConstants.SET_HEIGHT), PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);
        loop.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (loop.isOver()) {
                    looped = !looped;
                    if (looped) {
                        loop.setColor(Color.RED);
                    } else {
                        loop.setColor(Color.WHITE);
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.addActor(create);
        this.addActor(loop);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        create.draw(batch, parentAlpha);
        loop.draw(batch, parentAlpha);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}