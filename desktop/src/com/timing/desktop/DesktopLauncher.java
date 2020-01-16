package com.timing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.timing.Timing;
import com.timing.config.Rules;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Rules.WORLD_WIDTH;
        config.height = Rules.WORLD_HEIGHT;
        config.vSyncEnabled = true;
        new LwjglApplication(new Timing(), config);
    }
}
