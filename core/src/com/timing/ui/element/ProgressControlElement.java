package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.utils.Assets;

import lombok.Getter;

public class ProgressControlElement extends Group {
    @Getter
    private static ProgressControlElement instance = new ProgressControlElement();

    private Button play;
    private Button pause;
    private Button stop;

    private ProgressControlElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.play = new Button(skin);
        this.play.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE - 3 * PaintConstants.BUTTON_WIDTH / 2, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.pause = new Button(skin);
        this.pause.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.stop = new Button(skin);
        this.stop.setBounds(Rules.WORLD_WIDTH / 2 + PaintConstants.BUTTON_SPACE + PaintConstants.BUTTON_WIDTH / 2, PaintConstants.PROGRESS_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.addActor(play);
        this.addActor(pause);
        this.addActor(stop);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        play.draw(batch, parentAlpha);
        pause.draw(batch, parentAlpha);
        stop.draw(batch, parentAlpha);

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

