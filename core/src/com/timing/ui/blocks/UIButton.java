package com.timing.ui.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Shuttle on 15/01/20.
 */

public class UIButton extends ImageButton {
    public UIButton(Drawable imageUp) {
        super(imageUp);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float a;
        if (isPressed()) {
            a = 0.38f * parentAlpha;
        } else if (isOver()) {
            a = parentAlpha;
        } else {
            a = 0.62f * parentAlpha;
        }
        super.draw(batch, a);
    }

    @Override
    public void setColor(Color color) {
        getImage().setColor(color);
    }
}
