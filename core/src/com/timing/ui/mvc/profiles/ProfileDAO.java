package com.timing.ui.mvc.profiles;

import java.util.List;
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

    @Getter
    @Setter
    private List<Value> blocks;

    private ProfileDAO() {
    }

    public void copy(ProfileDAO profile) {
        this.type = profile.getType();
        this.name = profile.getName();
        this.preferences = profile.getPreferences();
        this.active = profile.isActive();
        this.blocks = profile.getBlocks();
    }

    public ProfileDAO(Type type, String name, Map<String, Object> preferences, boolean active, List<Value> blocks) {
        this.type = type;
        this.name = name;
        this.preferences = preferences;
        this.active = active;
        this.blocks = blocks;
        this.id = UUID.randomUUID().toString();
    }

    public static class Value {
        @Getter
        @Setter
        private int work;
        @Getter
        @Setter
        private int rest;

        public Value() {
        }

        public Value(int work, int rest) {
            this.work = work;
            this.rest = rest;
        }
    }

    public enum Type {
        PROFILE, DUMMY
    }
}