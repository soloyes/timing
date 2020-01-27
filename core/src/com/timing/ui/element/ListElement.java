package com.timing.ui.element;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.TimeBlock;
import com.timing.ui.mvc.profiles.ProfileDAO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ListElement extends Group {
    private static int width = Rules.WORLD_WIDTH / 2;
    @Getter
    private int totalWork = 0;
    @Getter
    private int totalRest = 0;

    private List<TimeBlock> list;

    public ListElement(ProfileDAO profileDAO) {
        this.list = new ArrayList<TimeBlock>();
        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            int sum = profileDAO.getBlocks().get(i).getRest() + profileDAO.getBlocks().get(i).getWork();
            float splitAmount = 1.0f * profileDAO.getBlocks().get(i).getWork() / sum;
            TimeBlock timeBlock = new TimeBlock(width / profileDAO.getBlocks().size() * i, 0, width / profileDAO.getBlocks().size(), PaintConstants.SET_HEIGHT, splitAmount);
            totalWork += profileDAO.getBlocks().get(i).getWork();
            totalRest += profileDAO.getBlocks().get(i).getRest();
            list.add(timeBlock);
            this.addActor(timeBlock);
        }
    }

    public ListElement(ProfileDAO.Values block) {
        this.list = new ArrayList<TimeBlock>();
        int sum = block.getRest() + block.getWork();
        float splitAmount = 1.0f * block.getWork() / sum;
        TimeBlock timeBlock = new TimeBlock(0, 0, width, PaintConstants.SET_HEIGHT, splitAmount);
        totalWork = block.getWork();
        totalRest = block.getRest();
        list.add(timeBlock);
        this.addActor(timeBlock);
    }
}