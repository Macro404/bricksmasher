package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Class controlling the games settings
 */
public class GameSettings {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "brickSettings";

    /**
     * @return the current settings
     */
    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    /**
     * @return true if sounds effects are enabled, false otherwise
     */
    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    /**
     * toggles sound effects on or off
     * @param soundEffectsEnabled true to enable, false to disable
     */
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    /**
     * @return true if music is enabled
     */
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    /**
     * @return the current music volume, ranging 0-1.0f
     */
    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    /**
     * @return the current sound volume, ranging 0-1.0f
     */
    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    /**
     * Sets the sound volume
     * @param volume the value to set volume to
     */
    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }


}
