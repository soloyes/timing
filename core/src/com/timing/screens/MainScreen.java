package com.timing.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ListGroup;
import com.timing.ui.group.ProgressGroup;

/**
 * @author Shuttle on 15/01/20.
 */

public class MainScreen extends BaseScreen {
    private Camera camera;
    private ProgressGroup progressGroup;
    private ListGroup listGroup;
    private ConfigGroup configGroup;

    public MainScreen(SpriteBatch batch, Camera camera) {
        super(batch);
        this.camera = camera;
    }

    @Override
    public void show() {
        super.show();
        this.progressGroup = ProgressGroup.getInstance();
        this.listGroup = ListGroup.getInstance();
        this.configGroup = ConfigGroup.getInstance();

        stage.addActor(progressGroup);
        stage.addActor(listGroup);
        stage.addActor(configGroup);
    }

    @Override
    public void createGUI() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        batch.setProjectionMatrix(camera.combined);
        stage.draw();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void dispose() {
        super.dispose();
        ListGroup.getInstance().dispose();
        ProgressGroup.getInstance().dispose();
    }
}
