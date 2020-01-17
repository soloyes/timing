package com.timing.ui.mvc.profiles;

import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Shuttle on 16/01/20.
 */

@ToString
public class ProfileDAO {
    @Getter
    @Setter
    private Type type;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Map<String, Object> preferences;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private String id;

    private ProfileDAO() {
    }

    public void copy(ProfileDAO profile) {
        this.type = profile.getType();
        this.name = profile.getName();
        this.preferences = profile.getPreferences();
        this.active = profile.isActive();
    }

    public ProfileDAO(Type type, String name, Map<String, Object> preferences, boolean active) {
        this.type = type;
        this.name = name;
        this.preferences = preferences;
        this.active = active;
        this.id = UUID.randomUUID().toString();
    }

    public enum Type {
        PROFILE, DUMMY
    }
}