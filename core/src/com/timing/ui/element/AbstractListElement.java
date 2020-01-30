package com.timing.ui.element;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.blocks.TimeBlock;

import java.util.List;

import lombok.Getter;

public abstract class AbstractListElement extends Group {
    protected static int width = Rules.WORLD_WIDTH / 2 - PaintConstants.LIST_ELEMENT_PAD;
    @Getter
    protected int totalWork = 0;
    @Getter
    protected int totalRest = 0;

    protected List<TimeBlock> list;
}
