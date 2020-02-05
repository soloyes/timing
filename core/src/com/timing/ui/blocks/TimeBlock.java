package com.timing.ui.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.timing.config.PaintConstants;
import com.timing.utils.Assets;

/**
 * @author Shuttle on 15/01/20.
 */

public class TimeBlock extends Group {
    private SplitPane pane;
    private SubBlock work;
    private SubBlock rest;
    private int width;
    private int height;
    private float splitAmount;

    public TimeBlock(int x, int y, int width, int height, final float splitAmount) {
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.width = width;
        this.height = height;
        this.splitAmount = splitAmount;
        this.work = new SubBlock(width / 2, height, new Color(Color.valueOf(PaintConstants.WORK_COLOR_HEX)));
        this.rest = new SubBlock(width / 2, height, new Color(Color.valueOf(PaintConstants.REST_COLOR_HEX)));
        this.pane = new SplitPane(work, rest, false, skin);
        this.pane.setBounds(x, y, width, height);
        pane.setSplitAmount(splitAmount);
        pane.clearListeners();
        //Empty black line in the middle of pane :)
        pane.getStyle().handle = new BaseDrawable();
        this.addActor(pane);
    }

    public void setSplitAmount(float splitAmount) {
        pane.setSplitAmount(splitAmount);
    }

    public void dispose() {
        work.dispose();
        rest.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        super.draw(batch, parentAlpha);
    }

    public void setA(float a) {
        work.setA(a);
        rest.setA(a);
    }
}
