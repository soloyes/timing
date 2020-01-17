package com.timing.screens;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timing.config.Rules;
import com.timing.utils.Assets;
import com.timing.utils.ScreenManager;

/**
 * @author Shuttle on 15/01/20.
 */

public class LoadingScreen extends BaseScreen {
    private SpriteBatch batch;
    private Texture texture;
    private Pixmap pixmap;

    public LoadingScreen(SpriteBatch batch) {
        super(batch);
        this.batch = batch;
        pixmap = new Pixmap(Rules.WORLD_WIDTH, (int) (Rules.WORLD_HEIGHT * (Rules.LOADING_HEIGHT / 100)), Pixmap.Format.RGB888);
        pixmap.setColor(1.0f, 0.5f, 0.5f, 1f);
        pixmap.fill();
        this.texture = new Texture(pixmap);
    }

    @Override
    public void createGUI() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Assets.getInstance().getAssetManager().update()) {
            Assets.getInstance().makeLinks();
            ScreenManager.getInstance().goToTarget();
        }
        batch.begin();
        batch.draw(texture, 0, 0, Rules.WORLD_WIDTH * Assets.getInstance().getAssetManager().getProgress(), (int) (Rules.WORLD_HEIGHT * (Rules.LOADING_HEIGHT / 100)));
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        texture.dispose();
        pixmap.dispose();
    }
}