package com.timing.ui.element;

import com.timing.config.PaintConstants;
import com.timing.ui.blocks.TimeBlock;
import com.timing.ui.mvc.profiles.ProfileDAO;

import java.util.ArrayList;

/**
 * @author Shuttle on 15/01/20.
 */

public class SimpleListElement extends AbstractListElement {
    private TimeBlock timeBlock;

    public SimpleListElement(ProfileDAO.Value block) {
        list = new ArrayList<TimeBlock>();
        int sum = block.getRest() + block.getWork();
        float splitAmount = 1.0f * block.getWork() / sum;
        timeBlock = new TimeBlock(0, -PaintConstants.SET_HEIGHT / 2, width, PaintConstants.SET_HEIGHT, splitAmount);
        totalWork = block.getWork();
        totalRest = block.getRest();
        list.add(timeBlock);
        this.addActor(timeBlock);
    }

    public void setSplitAmount(float splitAmount) {
        timeBlock.setSplitAmount(splitAmount);
    }
}