package com.timing.ui.element;

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
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shuttle on 16/01/20.
 */

public class ProgressControlElement extends Group {
    @Getter
    private static ProgressControlElement instance = new ProgressControlElement();

    @Getter
    @Setter
    private boolean on = false;

    private Button play;
    private Button stop;
    private TextureRegionDrawable up;
    private TextureRegionDrawable down;

    private BoomBox boomBox;

    private ProgressControlElement() {
        this.boomBox = new BoomBox();
        this.up = new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_PLAY));
        this.down = new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_PAUSE));
        this.play = new UIButton(up);
        this.play.setBounds(Rules.WORLD_WIDTH / 6 - PaintConstants.BUTTON_WIDTH - PaintConstants.LIST_ELEMENT_PAD, PaintConstants.PROGRESS_ELEMENT_HEIGHT + (PaintConstants.PLAY_PROGRESS_BAR_HEIGHT - PaintConstants.BUTTON_HEIGHT) / 2,
                PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT
        );
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (play.isOver()) {
                    on = !on;
                    if (on) {
                        play.getStyle().checked = down;
                    } else {
                        down = (TextureRegionDrawable) play.getStyle().checked;
                        play.getStyle().checked = null;
                    }
                }
            }
        });

        this.stop = new UIButton(
                new TextureRegionDrawable(Assets.getInstance().getAtlas().findRegion(PaintConstants.BUTTON_STOP))
        );
        this.stop.setBounds(5 * Rules.WORLD_WIDTH / 6 + PaintConstants.LIST_ELEMENT_PAD, PaintConstants.PROGRESS_ELEMENT_HEIGHT + (PaintConstants.PLAY_PROGRESS_BAR_HEIGHT - PaintConstants.BUTTON_HEIGHT) / 2,
                PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT
        );
        stop.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boomBox.playSound(MSConstants.UI_MENU);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (stop.isOver()) {
                    ProgressElement.getInstance().reset();
                    reset();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.addActor(play);
        this.addActor(stop);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        play.draw(batch, parentAlpha);
        stop.draw(batch, parentAlpha);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void reset() {
        play.setChecked(false);
    }
}