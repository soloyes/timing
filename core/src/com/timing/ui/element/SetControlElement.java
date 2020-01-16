package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.utils.Assets;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class SetControlElement extends Group {
    @Getter
    private static SetControlElement instance = new SetControlElement();

    private Button create;
    private Button config;
    private Button loop;

    private SetControlElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.create = new Button(skin);
        this.create.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE - 3 * PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.config = new Button(skin);
        this.config.setBounds(Rules.WORLD_WIDTH / 2 - PaintConstants.BUTTON_SPACE, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.loop = new Button(skin);
        this.loop.setBounds(Rules.WORLD_WIDTH / 2 + PaintConstants.BUTTON_SPACE + PaintConstants.BUTTON_WIDTH / 2, PaintConstants.SET_CONTROL_ELEMENT_HEIGHT, PaintConstants.BUTTON_WIDTH, PaintConstants.BUTTON_HEIGHT);

        this.addActor(create);
        this.addActor(config);
        this.addActor(loop);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        create.draw(batch, parentAlpha);
        config.draw(batch, parentAlpha);
        loop.draw(batch, parentAlpha);

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
