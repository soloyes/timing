package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.TimeBlock;
import com.timing.utils.Assets;
import com.timing.utils.ScreenManager;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class SetElement extends Group {
    @Getter
    private static SetElement instance = new SetElement();
    private TimeBlock timeBlock;
    private GlyphLayout glyphLayout;
    private BitmapFont font32;

    private SetElement() {
        this.glyphLayout = new GlyphLayout();
        int width = Rules.WORLD_WIDTH / 2;
        this.timeBlock = new TimeBlock((Rules.WORLD_WIDTH - 100) / 2, PaintConstants.SET_ELEMENT_HEIGHT, Rules.WORLD_WIDTH / 2, PaintConstants.SET_HEIGHT, 0.5f);
        this.font32 = Assets.getInstance().getAssetManager().get(PaintConstants.FONT32);
        font32.setColor(0.8f, 0.43f, 0.33f, 1f);

        this.addActor(timeBlock);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        glyphLayout.setText(font32, String.valueOf(timeBlock.getSplitAmount()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font32.draw(batch, glyphLayout,
                ScreenManager.getInstance().getViewport().getWorldWidth() / 2 - glyphLayout.width / 2,
                PaintConstants.SET_ELEMENT_HEIGHT
        );
    }

    public void dispose() {
        timeBlock.dispose();
    }
}
