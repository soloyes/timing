package com.timing.config;

/**
 * @author Shuttle on 15/01/20.
 */

public final class Rules {
    public static final boolean DEBUG = false;

    private Rules() {
    }

    //1920
    public static final int WORLD_WIDTH = 960;
    //1080
    public static final int WORLD_HEIGHT = 540;
    //Percentage 5
    public static final float LOADING_HEIGHT = 5.0f;

    //1.0f
    public static final float LINE_PRESSED_WAIT = 0.5f;
    //3.0f
    public static final float LINE_DELETE_WAIT = 2.0f;

    //
    public static final int DEFAULT_WORK_TIME = 30;
    public static final int DEFAULT_REST_TIME = 30;

    //
    public static final int GRANULARITY = 100;
}