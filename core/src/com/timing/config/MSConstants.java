package com.timing.config;

/**
 * @author Shuttle on 16/01/20.
 * Music and Sound constants (MSConstants)
 */

public final class MSConstants {
    private MSConstants() {
    }

    private static final String SOUND_ROOT_FOLDER = "sound/";
    private static final String UI_FOLDER = "ui/";
    private static final String UI = SOUND_ROOT_FOLDER + UI_FOLDER;

    private static final String MUSIC_ROOT_FOLDER = "music/";
    private static final String MUSIC_FOLDER = "music/";
    private static final String EFFECT_FOLDER = "effect/";
    private static final String MUSIC_EFFECT_FOLDER = MUSIC_ROOT_FOLDER + EFFECT_FOLDER;
    private static final String SONG_FOLDER = MUSIC_ROOT_FOLDER + MUSIC_FOLDER;

    //SOUND
    public static final String UI_CHANGED = UI + "ui_changed.ogg";
    public static final String UI_INPUT = UI + "ui_input.ogg";
    public static final String UI_CLICK = UI + "ui_click.ogg";
    public static final String UI_MENU = UI + "ui_menu_button.ogg";
    public static final String UI_PAUSE = UI + "ui_pause_button.ogg";
    //

    //SONG/MUSIC
    //

    //MUSIC EFFECT
    public static final String HUM_MUSIC_EFFECT_1 = MUSIC_EFFECT_FOLDER + "hum_1.ogg";
    public static final String HUM_MUSIC_EFFECT_2 = MUSIC_EFFECT_FOLDER + "peep_signal.ogg";
    //
}