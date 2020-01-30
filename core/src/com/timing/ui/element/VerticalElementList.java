package com.timing.ui.element;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ListGroup;
import com.timing.ui.group.ProgressGroup;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.utils.Assets;
import com.timing.utils.BoomBox;
import com.timing.utils.DigitFilter;
import com.timing.utils.DigitInputListener;

import java.util.HashSet;
import java.util.Set;

public class VerticalElementList extends VerticalGroup {
    private final ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private Skin skin;
    private ProfileDAO profileDAO;
    private Profiles profiles;
    private BoomBox boomBox;
    private Set<Line> lines;

    public VerticalElementList() {
        skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.verticalGroup = new VerticalGroup();
        this.boomBox = new BoomBox();
        this.lines = new HashSet<Line>();
        scrollPane = new ScrollPane(verticalGroup, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.getStyle().background = null;
        scrollPane.setForceScroll(false, true);

        Container<ScrollPane> container = new Container<ScrollPane>(scrollPane);
        container.setPosition(Rules.WORLD_WIDTH / 2, Rules.WORLD_HEIGHT / 2);
        container.width(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH + PaintConstants.LIST_EPSILON_WIDTH);
        container.height(Rules.WORLD_HEIGHT / 2);
        container.align(Align.top);
        this.setPosition(Rules.WORLD_WIDTH / 6, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.setWidth(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH + PaintConstants.LIST_EPSILON_WIDTH);
        this.setHeight(Rules.WORLD_HEIGHT / 2);

        this.profiles = new Profiles();
        this.addActor(container);
    }

    public void show(ProfileDAO profileDAO) {
        verticalGroup.clear();
        lines.clear();
        this.profileDAO = profileDAO;
        verticalGroup.addActor(new Head());
        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            Line line = new Line(new SimpleListElement(profileDAO.getBlocks().get(i)), profileDAO.getBlocks().get(i));
            verticalGroup.addActor(line);
            lines.add(line);
        }
    }

    public void showActive() {
        ProfileDAO profileDAO = profiles.getActive();
        if (profileDAO != null) {
            show(profileDAO);
            ConfigGroup.getInstance().switchVisible();
            ProgressGroup.getInstance().switchVisible();
        }
    }

    private class Head extends Group {
        private Table table;

        Head() {
            this.table = new Table();
            table.row().expandX();
            final TextButton plus = new TextButton(PaintConstants.UI_ADD_NEW_BLOCK, skin);
            plus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ProfileDAO.Values block = new ProfileDAO.Values(30, 30);
                    profileDAO.getBlocks().add(block);
                    Line line = new Line(new SimpleListElement(block), block);
                    lines.add(line);
                    verticalGroup.addActor(line);
                    ListGroup.getInstance().update();
                    ProgressGroup.getInstance().update();
                    boomBox.playSound(MSConstants.UI_CLICK);
                }
            });
            table.add();
            table.add(plus).center();
            table.add();
            table.align(Align.bottomLeft);
            table.setFillParent(true);
            this.setWidth(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH);
            this.setHeight(45);
            this.addActor(table);
        }
    }

    private class Line extends Group {
        private Table table;
        private Table innerTable;
        private TextField work;
        private TextField rest;
        private ProfileDAO.Values block;

        Line(final SimpleListElement simpleListElement, final ProfileDAO.Values block) {
            this.block = block;
            this.table = new Table();
            table.row();
            Label.LabelStyle style32 = new Label.LabelStyle(
                    Assets.getInstance().getAssetManager().get(PaintConstants.FONT32, BitmapFont.class),
                    new Color(0.8f, 0.43f, 0.33f, 1f)
            );
            Label minus = new Label("-", style32);
            minus.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    profileDAO.getBlocks().remove(block);
                    verticalGroup.removeActor(Line.this);
                    lines.remove(Line.this);
                    if (profileDAO.getBlocks().size() == 0) {
                        profiles.remove(profileDAO);
                        ListGroup.getInstance().switchVisible();
                        ConfigGroup.getInstance().switchVisible();
                    }
                    ListGroup.getInstance().update();
                    ProgressGroup.getInstance().update();
                    boomBox.playSound(MSConstants.UI_CHANGED);
                }
            });

            table.add(minus).width(PaintConstants.MINUS_WIDTH);
            table.add(simpleListElement).width((Rules.WORLD_WIDTH / 2) - PaintConstants.LIST_ELEMENT_PAD).padRight(PaintConstants.LIST_ELEMENT_PAD);

            this.innerTable = new Table();
            this.work = new TextField("", skin);
            work.setMaxLength(3);
            work.setText(String.valueOf(simpleListElement.getTotalWork()));
            work.setTextFieldFilter(new DigitFilter());
            work.addListener(new DigitInputListener(work));

            this.rest = new TextField("", skin);
            rest.setMaxLength(3);
            rest.setText(String.valueOf(simpleListElement.getTotalRest()));
            rest.setTextFieldFilter(new DigitFilter());
            rest.addListener(new DigitInputListener(rest));

            work.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int w, r;
                    w = work.getText().isEmpty() || work.getText().equals("") ? 0 : Integer.valueOf(work.getText());
                    r = rest.getText().isEmpty() || rest.getText().equals("") ? 0 : Integer.valueOf(rest.getText());
                    simpleListElement.setSplitAmount(1.0f * w / (w + r));
                    boomBox.playSound(MSConstants.UI_INPUT);
                    work.setColor(Color.RED);
                }
            });

            work.addListener(new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    if (keycode == Input.Keys.ENTER) {
                        boomBox.playSound(MSConstants.UI_CLICK);
                        for (Line l : lines) {
                            l.save();
                        }
                        ListGroup.getInstance().update();
                        ProgressGroup.getInstance().update();
                    }
                    return super.keyDown(event, keycode);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    boomBox.playSound(MSConstants.UI_CLICK);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

            rest.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int w, r;
                    w = work.getText().isEmpty() || work.getText().equals("") ? 0 : Integer.valueOf(work.getText());
                    r = rest.getText().isEmpty() || rest.getText().equals("") ? 0 : Integer.valueOf(rest.getText());
                    simpleListElement.setSplitAmount(1 - 1.0f * r / (w + r));
                    boomBox.playSound(MSConstants.UI_INPUT);
                    rest.setColor(Color.RED);
                }
            });

            rest.addListener(new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    if (keycode == Input.Keys.ENTER) {
                        boomBox.playSound(MSConstants.UI_CLICK);
                        for (Line l : lines) {
                            l.save();
                        }
                        ListGroup.getInstance().update();
                        ProgressGroup.getInstance().update();
                    }
                    return super.keyDown(event, keycode);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    boomBox.playSound(MSConstants.UI_CLICK);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

            innerTable.row();
            innerTable.add(work).width(PaintConstants.TEXT_FIELD_WIDTH / 2);
            innerTable.add(rest).width(PaintConstants.TEXT_FIELD_WIDTH / 2);

            table.add(innerTable).width(PaintConstants.TEXT_FIELD_WIDTH);
            table.align(Align.bottomLeft);
            table.setFillParent(true);

            this.setWidth(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH);
            this.setHeight(45);
            this.addActor(table);
        }

        private void save() {
            work.setColor(Color.WHITE);
            rest.setColor(Color.WHITE);
            setTextFields();
        }

        private void setTextFields() {
            int w, r;
            w = work.getText().isEmpty() || work.getText().equals("") ? 0 : Integer.valueOf(work.getText());
            r = rest.getText().isEmpty() || rest.getText().equals("") ? 0 : Integer.valueOf(rest.getText());
            block.setWork(w);
            block.setRest(r);
        }
    }
}