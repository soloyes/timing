package com.timing.config;

/**
 * @author Shuttle on 15/01/20.
 */

public final class Rules {
    public static final boolean DEBUG = true;

    private Rules() {
    }

    //1920
    public static final int WORLD_WIDTH = 960;
    //1080
    public static final int WORLD_HEIGHT = 540;
    //Percentage 10
    public static final float LOADING_HEIGHT = 10.0f;

    //1.0f
    public static final float LINE_PRESSED_WAIT = 1.0f;
    //3.0f
    public static final float LINE_DELETE_WAIT = 3.0f;

    //
    public static final int DEFAULT_WORK_TIME = 30;
    public static final int DEFAULT_REST_TIME = 30;

    //MUSIC
    //2
    public static final int MUSIC_EFFECT_PROBABILITY = 2;
}
