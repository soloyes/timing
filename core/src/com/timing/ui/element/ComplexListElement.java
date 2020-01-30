package com.timing.ui.element;

import com.timing.config.PaintConstants;
import com.timing.ui.blocks.TimeBlock;
import com.timing.ui.mvc.profiles.ProfileDAO;

import java.util.ArrayList;

public class ComplexListElement extends AbstractListElement {

    public ComplexListElement(ProfileDAO profileDAO) {
        this.list = new ArrayList<TimeBlock>();
        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            int sum = profileDAO.getBlocks().get(i).getRest() + profileDAO.getBlocks().get(i).getWork();
            float splitAmount = 1.0f * profileDAO.getBlocks().get(i).getWork() / sum;
            TimeBlock timeBlock = new TimeBlock(width / profileDAO.getBlocks().size() * i, -PaintConstants.SET_HEIGHT / 2, width / profileDAO.getBlocks().size(), PaintConstants.SET_HEIGHT, splitAmount);
            totalWork += profileDAO.getBlocks().get(i).getWork();
            totalRest += profileDAO.getBlocks().get(i).getRest();
            list.add(timeBlock);
            this.addActor(timeBlock);
        }
    }
}
