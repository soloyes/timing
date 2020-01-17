package com.timing.ui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.utils.Assets;
import com.timing.utils.ScreenManager;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shuttle on 16/01/20.
 */

public class SetElement extends Group {
    @Getter
    private static SetElement instance = new SetElement();
    private Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
    private TimeBlock timeBlock;
    private GlyphLayout glyphLayout;
    private BitmapFont font32;

    private SetElement() {
        this.glyphLayout = new GlyphLayout();
        this.timeBlock = new TimeBlock(Rules.WORLD_WIDTH / 2, PaintConstants.SET_HEIGHT);
        this.font32 = Assets.getInstance().getAssetManager().get(PaintConstants.FONT32);
        font32.setColor(0.8f, 0.43f, 0.33f, 1f);
        this.addActor(timeBlock);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        glyphLayout.setText(font32, String.valueOf(timeBlock.getSplitAmount()));
    }

    private class SubBlock extends Actor {
        private Pixmap pixmap;
        private Texture texture;
        @Getter
        @Setter
        private Color color;
        private int width;
        private int height;

        public SubBlock(int halfWidth, int height, Color color) {
            this.color = color;
            this.width = 2 * halfWidth;
            this.height = height;
            this.pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
            this.pixmap.setColor(color);
            this.pixmap.fill();
            this.texture = new Texture(pixmap);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(texture, 0, 0, width, height);
        }

        public void dispose() {
            texture.dispose();
            pixmap.dispose();
        }
    }

    private class TimeBlock extends Group {
        private SplitPane pane;
        private SubBlock work;
        private SubBlock rest;

        private int width;
        private int height;

        public TimeBlock(int width, int height) {
            this.width = width;
            this.height = height;
            work = new SubBlock(width / 2, height, new Color(0.0f, 0.0f, 1.0f, 1.0f));
            rest = new SubBlock(width / 2, height, new Color(1.0f, 0.5f, 0.0f, 1.0f));
            this.pane = new SplitPane(work, rest, false, skin);
            this.pane.setBounds((Rules.WORLD_WIDTH - width) / 2, PaintConstants.SET_ELEMENT_HEIGHT, width, height);
            this.addActor(pane);
        }

        public float getSplitAmount() {
            return pane.getSplitAmount();
        }

        public void dispose() {
            work.dispose();
            rest.dispose();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font32.draw(batch, glyphLayout,
                ScreenManager.getInstance().getViewport().getWorldWidth() / 2 - glyphLayout.width / 2,
                PaintConstants.SET_ELEMENT_HEIGHT
        );
    }

    public void dispose(){
        timeBlock.dispose();
    }
}
