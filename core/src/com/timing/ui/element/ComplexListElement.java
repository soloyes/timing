package com.timing.ui.element;

import com.timing.config.PaintConstants;
import com.timing.ui.blocks.TimeBlock;
import com.timing.ui.mvc.profiles.ProfileDAO;

import java.util.ArrayList;

/**
 * @author Shuttle on 15/01/20.
 */

public class ComplexListElement extends AbstractListElement {

    public ComplexListElement(ProfileDAO profileDAO) {
        this.list = new ArrayList<TimeBlock>();
        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            totalWork += profileDAO.getBlocks().get(i).getWork();
            totalRest += profileDAO.getBlocks().get(i).getRest();
        }

        int total = totalWork + totalRest;
        int x = 0;

        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            int sum = profileDAO.getBlocks().get(i).getRest() + profileDAO.getBlocks().get(i).getWork();
            float splitAmount = 1.0f * profileDAO.getBlocks().get(i).getWork() / sum;
            int w = (int) (width * (1.0f * sum / total));
            TimeBlock timeBlock = new TimeBlock(x, -PaintConstants.SET_HEIGHT / 2, w, PaintConstants.SET_HEIGHT, splitAmount);
            x += w;
            list.add(timeBlock);
            this.addActor(timeBlock);
        }
    }

    public void setA(float a) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setA(a);
        }
    }
}
