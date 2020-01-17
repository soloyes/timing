package com.timing.ui.mvc.profiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.Controller;
import com.timing.ui.mvc.View;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shuttle on 16/01/20.
 */

public class VerticalGroupList extends VerticalGroup implements View<ProfileDAO> {
    private Controller<ProfileDAO> controller;
    private final ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private Map<String, Line> currentLines;
    private Map<String, Line> previousLines;
    private List<String> id;
    private BoomBox boomBox;

    public VerticalGroupList() {
        this.boomBox = new BoomBox();
        Skin skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.currentLines = new HashMap<String, Line>();
        this.previousLines = new HashMap<String, Line>();
        this.id = new ArrayList<String>();
        this.verticalGroup = new VerticalGroup();
        scrollPane = new ScrollPane(verticalGroup, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        //scrollPane.setVariableSizeKnobs(false);
        scrollPane.setVisible(false);
        Container<ScrollPane> container = new Container<ScrollPane>(scrollPane);
        container.setPosition(Rules.WORLD_WIDTH / 2, Rules.WORLD_HEIGHT / 2);
        container.height(Rules.WORLD_HEIGHT / 2);
        container.width(Rules.WORLD_WIDTH / 2);
        container.align(Align.top);
        this.setPosition(Rules.WORLD_WIDTH / 4, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.setWidth(Rules.WORLD_WIDTH / 2);
        this.setHeight(Rules.WORLD_HEIGHT / 2);

        this.addActor(container);
    }

    @Override
    public void setController(Controller<ProfileDAO> controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
        verticalGroup.clear();
        verticalGroup.addActor(new Header());
        boolean hasActive = false;
        for (int i = 0; i < controller.getList().size(); i++) {
            ProfileDAO profile = controller.getList().get(i);
            Line line = new Line(profile);
            currentLines.put(profile.getId(), line);
            verticalGroup.addActor(line);
        }
        id.clear();
        id.addAll(previousLines.keySet());
        for (int i = 0; i < id.size(); i++) {
            Line line = currentLines.get(id.get(i));
            if (line != null) {
                line
                        .getTimeAndFlag()
                        .copy(previousLines.get(id.get(i)).getTimeAndFlag());
            }
        }
        previousLines.clear();
        previousLines.putAll(currentLines);
        currentLines.clear();
    }

    public void switchVisible() {
        scrollPane.setVisible(!scrollPane.isVisible());
    }

    private class TimeAndFlag {
        @Getter
        @Setter
        private boolean pressed;
        @Getter
        @Setter
        private boolean confirm;
        @Getter
        @Setter
        private float pressedWait;
        @Getter
        @Setter
        private float deleteWait;

        void addPressedWait(float amount) {
            pressedWait += amount;
        }

        void addDeleteWait(float amount) {
            deleteWait += amount;
        }

        public void copy(TimeAndFlag timeAndFlag) {
            this.confirm = timeAndFlag.confirm;
            this.pressed = timeAndFlag.pressed;
            this.pressedWait = timeAndFlag.pressedWait;
            this.deleteWait = timeAndFlag.deleteWait;
        }
    }

    private class Line extends Group {
        private Table table;
        private final ProfileDAO profile;
        @Getter
        private TimeAndFlag timeAndFlag;

        @Override
        public void act(float delta) {
            super.act(delta);
            if (timeAndFlag.isPressed()) {
                timeAndFlag.addPressedWait(delta);
            }
            if (timeAndFlag.getPressedWait() >= Rules.LINE_PRESSED_WAIT) {
                timeAndFlag.setPressedWait(0.0f);
                timeAndFlag.setPressed(false);
                timeAndFlag.setConfirm(true);
            }
            if (timeAndFlag.isConfirm()) {
                timeAndFlag.addDeleteWait(delta);
            }
            if (timeAndFlag.getDeleteWait() >= Rules.LINE_DELETE_WAIT) {
                timeAndFlag.setDeleteWait(0.0f);
                timeAndFlag.setConfirm(false);
                controller.remove(Line.this.profile);
                boomBox.playSound(MSConstants.UI_CHANGED);
            }
        }

        Line(final ProfileDAO profile) {
            this.timeAndFlag = new TimeAndFlag();
            this.profile = profile;
            this.table = new Table();
            table.row().padBottom(22).expandX();
            Label.LabelStyle style64 = new Label.LabelStyle(
                    Assets.getInstance().getAssetManager().get(PaintConstants.FONT32, BitmapFont.class),
                    new Color(0.8f, 0.43f, 0.33f, 1f)
            );
            Label minus = new Label("-", style64);
            minus.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (!timeAndFlag.isConfirm()) {
                        timeAndFlag.setPressed(true);
                    } else {
                        timeAndFlag.setConfirm(false);
                    }
                    boomBox.playSound(MSConstants.UI_CLICK);

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    timeAndFlag.setPressed(false);
                    timeAndFlag.setDeleteWait(0.0f);
                    timeAndFlag.setPressedWait(0.0f);
                }
            });

            if (profile.getType() == ProfileDAO.Type.DUMMY) {
                table.add();
                table.add();
                table.add();
            } else {
                table.add(minus);
                Label label = new Label(profile.getName(), style64);
                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        controller.select(profile);
                        boomBox.playSound(MSConstants.UI_CHANGED);
                    }
                });
                table.add(label).center();
                table.add();
            }

            table.align(Align.bottomLeft);
            table.setFillParent(true);
            this.setWidth(Rules.WORLD_WIDTH / 2);
            this.setHeight(90);
            this.addActor(table);
        }
    }

    private class Header extends Group {
        private Table table;

        Header() {
            this.table = new Table();
            table.row().padBottom(22).expandX();
            Label.LabelStyle style64 = new Label.LabelStyle(
                    Assets.getInstance().getAssetManager().get(PaintConstants.FONT32, BitmapFont.class),
                    new Color(0.8f, 0.43f, 0.33f, 1f)
            );
            Label plus = new Label("+", style64);
            plus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.add();
                    boomBox.playSound(MSConstants.UI_CLICK);
                }
            });
            table.add(plus);
            table.add(new Label(PaintConstants.UI_ADD_NEW_LINE, style64)).center();
            table.add();
            table.align(Align.bottomLeft);
            table.setFillParent(true);
            this.setWidth(Rules.WORLD_WIDTH / 2);
            this.setHeight(90);
            this.addActor(table);
        }
    }

    public ProfileDAO addProfile(String username) {
        setVisible(true);
        return controller.add(username);
    }
}