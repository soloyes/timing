package com.timing.ui.mvc.profiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.timing.config.AppPreferences;
import com.timing.config.FileConstants;
import com.timing.config.PaintConstants;
import com.timing.config.Rules;
import com.timing.ui.mvc.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuttle on 16/01/20.
 */

//todo: Сделать нормальный синглтон? А то везде зову этот класс, и каждый раз создаётся контекст. Может быть я был не прав в таком подходе, надо попробовать переделать.
public class Profiles implements Model<ProfileDAO> {
    private static Profiles instance;
    private static boolean empty;
    private List<ProfileDAO> profiles;
    private FileHandle file;

    public Profiles() {
        this.profiles = new ArrayList<ProfileDAO>();
        this.file = Gdx.files.local(FileConstants.PROFILES);
    }

    private static ProfileDAO asEmpty() {
        ArrayList<ProfileDAO.Values> blocks = new ArrayList<ProfileDAO.Values>();
        blocks.add(new ProfileDAO.Values(Rules.DEFAULT_WORK_TIME, Rules.DEFAULT_REST_TIME));
        return new ProfileDAO(ProfileDAO.Type.PROFILE, PaintConstants.UI_EMPTY_PROFILE, AppPreferences.defaults, false, blocks);
    }

    @Override
    public ProfileDAO addEmpty() {
        preferencesToActiveProfile();
        ProfileDAO empty = asEmpty();
        getList().add(empty);
        return empty;
    }

    @Override
    public void addDummy() {
        ProfileDAO dummy = asDummy();
        getList().add(dummy);
    }

    @Override
    public void write() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (instance.file.exists()) {
                mapper.writeValue(instance.file.file(), instance.profiles);
            } else {
                initDefaultProfiles();
            }
        } catch (IOException e) {
            initDefaultProfiles();
            e.printStackTrace();
        }

        empty = true;
        for (int i = 0; i < getList().size(); i++) {
            ProfileDAO profile = getList().get(i);
            if (profile.getType() != ProfileDAO.Type.DUMMY) {
                empty = false;
                break;
            }
        }

        if (getActive() != null) {
            AppPreferences.apply(getActive().getPreferences());
        } else {
            AppPreferences.apply(AppPreferences.defaults);
        }
    }

    private static ProfileDAO asDummy() {
        ArrayList<ProfileDAO.Values> blocks = new ArrayList<ProfileDAO.Values>();
        blocks.add(new ProfileDAO.Values(Rules.DEFAULT_WORK_TIME, Rules.DEFAULT_REST_TIME));
        return new ProfileDAO(ProfileDAO.Type.DUMMY, "", null, false, blocks);
    }

    private void initDefaultProfiles() {
        if (getList().size() < PaintConstants.DUMMY_PROFILES) {
            for (int i = 0; i < PaintConstants.DUMMY_PROFILES; i++) {
                getList().add(asDummy());
            }
        }
    }

    private void preferencesToActiveProfile() {
        if (getActive() != null) {
            getActive().setPreferences(AppPreferences.getCurrent());
        }
    }

    //Why? Because it is not good to work with static resources (file e.g.)
    private Profiles getInstance() {
        if (instance == null) {
            instance = new Profiles();
            //First app start
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (instance.file.exists()) {
                    CollectionLikeType collectionLikeType =
                            TypeFactory
                                    .defaultInstance()
                                    .constructCollectionLikeType(List.class, ProfileDAO.class);
                    instance.profiles = mapper.readValue(instance.file.file(), collectionLikeType);
                } else {
                    initDefaultProfiles();
                    mapper.writeValue(instance.file.file(), instance.profiles);
                }
            } catch (IOException e) {
                initDefaultProfiles();
                e.printStackTrace();
            }
            //
        }
        return instance;
    }

    public static boolean isEmpty() {
        return empty;
    }

    @Override
    public void setEmpty(ProfileDAO profile) {
        preferencesToActiveProfile();
        profile.copy(asEmpty());
    }

    @Override
    public void remove(ProfileDAO profile) {
        List<ProfileDAO> profiles = getList();
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(profile.getId())) {
                if (profile.isActive()) {
                    if (i == 0) {
                        profiles.get(i + 1).setActive(true);
                    } else {
                        profiles.get(i - 1).setActive(true);
                    }
                }

                profiles.remove(profile);

                //If have we deleted all from list
                ProfileDAO tmpProfile = profiles.get(0);
                if (tmpProfile.getType() == ProfileDAO.Type.DUMMY) {
                    tmpProfile.setActive(false);
                }
                break;
            }
        }
    }

    public void dispose() {
        preferencesToActiveProfile();
        write();
    }

    public void flush() {
        dispose();
    }

    @Override
    public void activate(ProfileDAO profile) {
        List<ProfileDAO> profiles = getList();
        preferencesToActiveProfile();
        for (int i = 0; i < profiles.size(); i++) {
            profiles.get(i).setActive(false);
        }
        profile.setActive(true);
    }

    @Override
    public ProfileDAO getActive() {
        List<ProfileDAO> profiles = getList();
        ProfileDAO profile = null;
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).isActive()) {
                profile = profiles.get(i);
            }
        }
        return profile;
    }

    @Override
    public List<ProfileDAO> getList() {
        return getInstance().profiles;
    }
}