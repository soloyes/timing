package com.timing.ui.element;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.TimeBlock;
import com.timing.ui.mvc.profiles.ProfileDAO;

public class ListElement extends Group {
    private ProfileDAO profileDAO;

    public ListElement(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
        int width = Rules.WORLD_WIDTH / 2;

        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            int sum = profileDAO.getBlocks().get(i).getRest() + profileDAO.getBlocks().get(i).getWork();
            float splitAmount = 1.0f * profileDAO.getBlocks().get(i).getWork() / sum;
            TimeBlock timeBlock = new TimeBlock(width / profileDAO.getBlocks().size() * i, 0, width / profileDAO.getBlocks().size(), PaintConstants.SET_HEIGHT, splitAmount);
            this.addActor(timeBlock);
        }
    }
}