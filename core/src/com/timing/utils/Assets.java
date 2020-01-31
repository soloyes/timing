package com.timing.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.timing.config.MSConstants;
import com.timing.config.PaintConstants;

/**
 * @author Shuttle on 15/01/20.
 */

public class Assets {
    private static Assets instance = new Assets();
    private AssetManager assetManager;
    private TextureAtlas atlas;

    private Assets() {
        this.assetManager = new AssetManager();
    }

    public void clear() {
        assetManager.clear();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MAIN:
                assetManager.load(PaintConstants.PACK, TextureAtlas.class);
                assetManager.load(PaintConstants.SKIN_FILE, Skin.class);

                assetManager.load(MSConstants.UI_CHANGED, Sound.class);
                assetManager.load(MSConstants.UI_MENU, Sound.class);
                assetManager.load(MSConstants.UI_CLICK, Sound.class);
                assetManager.load(MSConstants.UI_INPUT, Sound.class);
                assetManager.load(MSConstants.UI_PAUSE, Sound.class);
                assetManager.load(MSConstants.UI_WORK, Sound.class);
                assetManager.load(MSConstants.UI_REST, Sound.class);
                createStandardFont(32, "");
                createStandardFont(64, "");
                createStandardFont(128, "");
                break;
        }
    }

    public void makeLinks() {
        atlas = assetManager.get(PaintConstants.PACK, TextureAtlas.class);
    }

    private void createStandardFont(int size, String postfix) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "fonts/CollegeRegular.ttf";
        fontParameter.fontParameters.size = size;
        fontParameter.fontParameters.color = Color.valueOf("ffffffff");
        fontParameter.fontParameters.borderWidth = 1;
        fontParameter.fontParameters.borderColor = Color.valueOf("000000ff");
        fontParameter.fontParameters.shadowOffsetX = 3;
        fontParameter.fontParameters.shadowOffsetY = 3;
        fontParameter.fontParameters.shadowColor = Color.valueOf("000000ff");
        assetManager.load("fonts/CollegeRegular" + size + postfix + ".ttf", BitmapFont.class, fontParameter);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public static Assets getInstance() {
        return instance;
    }
}