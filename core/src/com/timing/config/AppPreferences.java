package com.timing.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuttle on 16/01/20.
 */

public class AppPreferences {
    private static final String PREFS_NAME = "timing_preferences";

    private static final String PREF_SOUND_VOLUME = "sound";
    private static final String PREF_EFFECTS_ENABLED = "effects.enabled";
    private static final String PREF_EFFECTS_VOLUME = "effects";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_MUSIC_VOLUME = "music";
    private static final String PREF_VIBRATION_ENABLED = "vibration.enabled";
    private static final String PREF_TIME_FORMAT = "time.format";

    private static Preferences preferences;

    public static Map<String, Object> defaults = new HashMap<String, Object>() {{
        put(PREF_SOUND_VOLUME, 1.0f);
        put(PREF_EFFECTS_ENABLED, true);
        put(PREF_EFFECTS_VOLUME, 0.5f);
        put(PREF_MUSIC_ENABLED, true);
        put(PREF_MUSIC_VOLUME, 0.5f);
        put(PREF_VIBRATION_ENABLED, true);
        put(PREF_TIME_FORMAT, 60.0f);
    }};

    public static void apply(Map<String, Object> preferences) {
        getInstance().put(preferences);
        getInstance().flush();
    }

    public static Map<String, Object> getCurrent() {
        return new HashMap<String, Object>(getInstance().get());
    }

    private AppPreferences() {
    }

    private static Preferences getInstance() {
        if (preferences == null)
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        return preferences;
    }

    public static float getOverallMusicVolume() {
        return getMusicVolume() * getSoundVolume();
    }

    public static float getOverallEffectsVolume() {
        return getEffectsVolume() * getSoundVolume();
    }

    public static float getSoundVolume() {
        return getInstance().getFloat(PREF_SOUND_VOLUME);
    }


    public static void setSoundVolume(float volume) {
        getInstance().putFloat(PREF_SOUND_VOLUME, volume);
        getInstance().flush();
    }

    public static boolean isMusicEnabled() {
        return getInstance().getBoolean(PREF_MUSIC_ENABLED);
    }

    public static void setMusicEnabled(boolean enabled) {
        getInstance().putBoolean(PREF_MUSIC_ENABLED, enabled);
        getInstance().flush();
    }

    public static float getMusicVolume() {
        return getInstance().getFloat(PREF_MUSIC_VOLUME);
    }

    public static void setMusicVolume(float volume) {
        getInstance().putFloat(PREF_MUSIC_VOLUME, volume);
        getInstance().flush();
    }

    public static boolean isEffectsEnabled() {
        return getInstance().getBoolean(PREF_EFFECTS_ENABLED);
    }

    public static void setEffectsEnabled(boolean enabled) {
        getInstance().putBoolean(PREF_EFFECTS_ENABLED, enabled);
        getInstance().flush();
    }

    public static float getEffectsVolume() {
        return getInstance().getFloat(PREF_EFFECTS_VOLUME);
    }

    public static void setEffectsVolume(float volume) {
        getInstance().putFloat(PREF_EFFECTS_VOLUME, volume);
        getInstance().flush();
    }

    public static boolean isVibrationEnabled() {
        return getInstance().getBoolean(PREF_VIBRATION_ENABLED);
    }

    public static void setVibrationEnabled(boolean enabled) {
        getInstance().putBoolean(PREF_VIBRATION_ENABLED, enabled);
        getInstance().flush();
    }

    public static float getTimeFormat() {
        return getInstance().getFloat(PREF_TIME_FORMAT);
    }

    public static void setTimeFormat(float format) {
        getInstance().putFloat(PREF_TIME_FORMAT, format);
        getInstance().flush();
    }
}