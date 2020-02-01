package com.timing.ui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.AppPreferences;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

import java.util.LinkedList;
import java.util.List;

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
    @Getter
    private float totalProgress;
    private LinkedList<Item> queue;
    private Item item;
    private BoomBox boomBox;

    private ProgressElement() {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.boomBox = new BoomBox();
        this.profiles = new Profiles();
        this.progressBar = new ProgressBar(0, 100, 1, false, skin);

        this.progressBar.getStyle().background.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.progressBar.getStyle().knobBefore.setMinHeight(PaintConstants.PLAY_PROGRESS_BAR_HEIGHT - 12);
        this.progressBar.setBounds(Rules.WORLD_WIDTH / 6, PaintConstants.PROGRESS_ELEMENT_HEIGHT, 2 * Rules.WORLD_WIDTH / 3, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.queue = new LinkedList<Item>();
        reset();
        checkLoop();
        progressBar.setColor(Color.valueOf(PaintConstants.WORK_COLOR_HEX));
        this.addActor(progressBar);
    }

    @Override
    public void act(float delta) {
        if (ProgressControlElement.getInstance().isOn()) {
            if (item == null) {
                checkLoop();
            } else {
                switch (item.type) {
                    case WORK:
                        progressBar.setColor(Color.valueOf(PaintConstants.WORK_COLOR_HEX));
                        break;
                    case REST:
                        progressBar.setColor(Color.valueOf(PaintConstants.REST_COLOR_HEX));
                }
                progress += delta;
                totalProgress += delta;
                progressBar.setValue(progress);
            }
        }

        if (progressBar.getPercent() >= 1.0f) {
            if (item != null) {
                switch (item.type) {
                    case WORK:
                        boomBox.playSound(MSConstants.UI_WORK);
                        break;
                    case REST:
                        boomBox.playSound(MSConstants.UI_REST);
                }
            }
            checkLoop();
            progress = 0.0f;
        }
    }

    private void checkLoop() {
        item = queue.poll();
        if (item != null) {
            progressBar.setRange(0.0f, item.value);
            progressBar.setStepSize(item.value / (AppPreferences.getTimeFormat() * Rules.GRANULARITY));
        } else {
            if (SetControlElement.getInstance().isLooped()) {
                reset();
                ProgressControlElement.getInstance().setOn(true);
            } else {
                ProgressControlElement.getInstance().setOn(false);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        progressBar.draw(batch, parentAlpha);
    }

    public void reset() {
        profileDAO = profiles.getActive();
        queue.clear();
        item = null;
        if (profileDAO != null) {
            List<ProfileDAO.Value> list = profileDAO.getBlocks();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getWork() != 0) {
                    Item item = new Item(Type.WORK, list.get(i).getWork());
                    queue.add(item);
                }
                if (list.get(i).getRest() != 0) {
                    Item item = new Item(Type.REST, list.get(i).getRest());
                    queue.add(item);
                }
            }
        }
        progressBar.setColor(Color.valueOf(PaintConstants.WORK_COLOR_HEX));
        progress = 0.0f;
        totalProgress = 0.0f;
        progressBar.setValue(progress);
        ProgressControlElement.getInstance().setOn(false);
    }

    private class Item {
        private Type type;
        private int value;

        Item(Type type, int value) {
            this.type = type;
            this.value = value;
        }
    }

    private enum Type {
        WORK,
        REST
    }
}