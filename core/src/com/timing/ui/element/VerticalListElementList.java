package com.timing.ui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ListGroup;
import com.timing.ui.mvc.profiles.ProfileDAO;
import com.timing.ui.mvc.profiles.Profiles;
import com.timing.utils.Assets;

public class VerticalListElementList extends VerticalGroup {
    private final ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private Skin skin;
    private ProfileDAO profileDAO;
    private Profiles profiles;

    public VerticalListElementList() {
        skin = Assets.getInstance().getAssetManager().get(PaintConstants.SKIN_FILE);
        this.verticalGroup = new VerticalGroup();
        scrollPane = new ScrollPane(verticalGroup, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        Container<ScrollPane> container = new Container<ScrollPane>(scrollPane);
        container.setPosition(Rules.WORLD_WIDTH / 2, Rules.WORLD_HEIGHT / 2);
        container.width(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH + 60);
        container.height(Rules.WORLD_HEIGHT / 2);
        container.align(Align.top);
        this.setPosition(Rules.WORLD_WIDTH / 6, PaintConstants.PLAY_PROGRESS_BAR_HEIGHT);
        this.setWidth(PaintConstants.MINUS_WIDTH + Rules.WORLD_WIDTH / 2 + PaintConstants.TEXT_FIELD_WIDTH + 60);
        this.setHeight(Rules.WORLD_HEIGHT / 2);

        this.profiles = new Profiles();
        this.addActor(container);
    }

    public void show(ProfileDAO profileDAO) {
        verticalGroup.clear();
        this.profileDAO = profileDAO;
        verticalGroup.addActor(new Head());
        for (int i = 0; i < profileDAO.getBlocks().size(); i++) {
            verticalGroup.addActor(new Line(new ListElement(profileDAO.getBlocks().get(i)), i));
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
                    profileDAO.getBlocks().add(new ProfileDAO.Values(30, 30));
                    verticalGroup.addActor(new Line(new ListElement(new ProfileDAO.Values(30, 30))));
                    ListGroup.getInstance().update();
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
        private int blockIndex;

        Line(final ListElement listElement) {
            this(listElement, 0);
            this.blockIndex = 0;
        }

        Line(final ListElement listElement, final int blockIndex) {
            this.blockIndex = blockIndex;
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
                    verticalGroup.removeActor(Line.this);
                    profileDAO.getBlocks().remove(blockIndex);
                    if (profileDAO.getBlocks().size() == 0) {
                        profiles.remove(profileDAO);
                        ListGroup.getInstance().switchVisible();
                        ConfigGroup.getInstance().switchVisible();
                    }
                    ListGroup.getInstance().update();
                    super.clicked(event, x, y);
                }
            });

            table.add(minus).width(PaintConstants.MINUS_WIDTH);
            table.add(listElement).width(Rules.WORLD_WIDTH / 2).bottom();

            this.innerTable = new Table();
            this.work = new TextField("", skin);
            work.setMaxLength(3);
            work.setText(String.valueOf(listElement.getTotalWork()));
            this.rest = new TextField("", skin);
            rest.setMaxLength(3);
            rest.setText(String.valueOf(listElement.getTotalRest()));
            innerTable.row();
            innerTable.add(work).width(PaintConstants.TEXT_FIELD_WIDTH / 2);
            innerTable.add(rest).width(PaintConstants.TEXT_FIELD_WIDTH / 2);

            table.add(innerTable).width(PaintConstants.TEXT_FIELD_WIDTH);
            table.align(Align.bottomLeft);
            table.setFillParent(true);

            this.setWidth(2 * Rules.WORLD_WIDTH / 3);
            this.setHeight(45);
            this.addActor(table);
        }
    }
}
