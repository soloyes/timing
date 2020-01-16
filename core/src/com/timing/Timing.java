package com.timing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timing.utils.ScreenManager;

/**
 * @author Shuttle on 15/01/20.
 */

public class Timing extends Game {
    private SpriteBatch batch;
    private ScreenManager.ScreenType screenType;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        if (screenType == null) {
            screenType = ScreenManager.ScreenType.MAIN;
        }
        ScreenManager.getInstance().changeScreen(screenType);
    }

    @Override
    public void dispose() {
        batch.dispose();
        getScreen().dispose();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }
}
