package com.timing.ui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;

import lombok.Getter;

/**
 * @author Shuttle on 16/01/20.
 */
public class SetElement extends Group {
    @Getter
    private static SetElement instance = new SetElement();
    private Profiles profiles;
    private ComplexListElement complexListElement;
    private Pixmap pixmap;
    private Texture texture;

    private SetElement() {
        this.profiles = new Profiles();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO == null) {
            complexListElement = null;
        } else {
            complexListElement = new ComplexListElement(profileDAO);
        }
        if (complexListElement != null) {
            this.addActor(complexListElement);
        }

        this.setPosition((Rules.WORLD_WIDTH / 4) + PaintConstants.LIST_ELEMENT_PAD / 2, PaintConstants.SET_ELEMENT_HEIGHT);

        this.pixmap = new Pixmap(Rules.WORLD_WIDTH / 2 - PaintConstants.LIST_ELEMENT_PAD, PaintConstants.SET_HEIGHT, Pixmap.Format.RGB888);
        pixmap.fill();
        this.texture = new Texture(pixmap);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = batch.getColor();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.6f);
        if (complexListElement != null) {
            batch.draw(texture,
                    (Rules.WORLD_WIDTH / 4) + PaintConstants.LIST_ELEMENT_PAD / 2, PaintConstants.SET_ELEMENT_HEIGHT - PaintConstants.SET_HEIGHT / 2,
                    (Rules.WORLD_WIDTH / 2 - PaintConstants.LIST_ELEMENT_PAD) * (ProgressElement.getInstance().getTotalProgress()) / (complexListElement.getTotalWork() + complexListElement.getTotalRest()), PaintConstants.SET_HEIGHT
            );
        }
        batch.setColor(color);
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }

    public void update() {
        this.clear();
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO != null) {
            complexListElement = new ComplexListElement(profileDAO);
            this.addActor(complexListElement);
        }
    }
}