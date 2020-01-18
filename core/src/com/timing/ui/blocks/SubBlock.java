package com.timing.ui.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import lombok.Getter;
import lombok.Setter;

public class SubBlock extends Actor {
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
