package com.timing.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.timing.config.Rules;
import com.timing.screens.LoadingScreen;
import com.timing.screens.MainScreen;

import lombok.Getter;

/**
 * @author Shuttle on 15/01/20.
 */

public class ScreenManager {
    private Game app;
    private static ScreenManager instance;
    private Screen targetScreen;
    private LoadingScreen loadingScreen;
    private MainScreen mainScreen;
    private SpriteBatch batch;
    private Viewport viewport;
    @Getter
    private Camera camera;
    @Getter
    private ScreenType currentScreen;

    private ScreenManager() {
    }

    public void changeScreen(ScreenType type) {
        Screen screen = app.getScreen();
        Gdx.input.setInputProcessor(null);
        Assets.getInstance().clear();
        if (screen != null) {
            screen.dispose();
        }
        resetCamera();
        app.setScreen(loadingScreen);
        currentScreen = type;
        switch (type) {
            case MAIN:
                targetScreen = mainScreen;
                Assets.getInstance().loadAssets(ScreenType.MAIN);
                break;
        }
    }

    public void resetCamera() {
        camera.position.set(Rules.WORLD_WIDTH / 2, Rules.WORLD_HEIGHT / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void goToTarget() {
        app.setScreen(targetScreen);
    }

    public void init(Game app, SpriteBatch batch) {
        this.app = app;
        this.batch = batch;
        this.camera = new OrthographicCamera(Rules.WORLD_WIDTH, Rules.WORLD_HEIGHT);
        this.viewport = new FitViewport(Rules.WORLD_WIDTH, Rules.WORLD_HEIGHT, camera);
        this.mainScreen = new MainScreen(batch, camera);
        this.loadingScreen = new LoadingScreen(batch);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public enum ScreenType {
        MAIN
    }
}
