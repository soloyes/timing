package com.timing.ui.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.AppPreferences;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.utils.Assets;

import java.util.LinkedList;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */

public class ProgressElement extends Group {
    @Getter
    private static ProgressElement instance = new ProgressElement();

    private ProgressBar progressBar;
    private Profiles profiles;
    private ProfileDAO profileDAO;
    private float progress;
    private LinkedList<Float> queue;
    private Float max;

    private ProgressElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.profiles = new Profiles();
        this.progressBar = new ProgressBar(0, 100, 1, false, skin);
        this.progressBar.getStyle().background.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.progressBar.getStyle().knobBefore.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT - 12);
        this.progressBar.setBounds(Rules.WORLD_WIDTH / 6, PaintConstants.PROGRESS_ELEMENT_HEIGHT, 2 * Rules.WORLD_WIDTH / 3, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.queue = new LinkedList<Float>();
        update();
        this.addActor(progressBar);
    }

    @Override
    public void act(float delta) {
        if (ProgressControlElement.getInstance().isOn() && max != null) {
            float max = this.max * AppPreferences.getTimeFormat();
            progressBar.setRange(0.0f, max);
            progressBar.setStepSize(max / (AppPreferences.getTimeFormat() * Rules.GRANULARITY));
            progress += delta;
            progressBar.setValue(progress);
            if (progressBar.getPercent() >= 1.0f) {
                progress = 0.0f;
                checkLoop();
            }
        }
    }

    private void checkLoop() {
        max = queue.poll();
        if (SetControlElement.getInstance().isLooped() && max == null) {
            update();
            ProgressControlElement.getInstance().setOn(true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        progressBar.draw(batch, parentAlpha);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void update() {
        profileDAO = profiles.getActive();
        queue.clear();
        if (profileDAO != null) {
            for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
                queue.add((float) profileDAO.getBlocks().get(i).getWork());
                queue.add((float) profileDAO.getBlocks().get(i).getRest());
            }
            max = queue.poll();
        }
        progress = 0.0f;
        progressBar.setValue(progress);
        ProgressControlElement.getInstance().setOn(false);
    }
}