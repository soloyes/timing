package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.utils.Assets;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class ProgressElement extends Group {
    @Getter
    private static ProgressElement instance = new ProgressElement();

    private ProgressBar progressBar;
    private float progress;

    private ProgressElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);

        this.progressBar = new ProgressBar(0, 100, 1, false, skin);
        this.progressBar.getStyle().background.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.progressBar.getStyle().knobBefore.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT - 12);
        this.progressBar.setBounds(Rules.WORLD_WIDTH / 6, PaintConstants.PROGRESS_ELEMENT_HEIGHT, 2 * Rules.WORLD_WIDTH / 3, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);

        this.addActor(progressBar);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        progress += delta * 60;
        progressBar.setValue(progress);
        if (progressBar.getPercent() >= 1.0f) {
            progress = 0.0f;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        progressBar.draw(batch, parentAlpha);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
