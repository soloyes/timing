package com.timing.ui.mvc;

import com.timing.ui.mvc.profiles.ProfileDAO;

import java.util.List;

/**
 * @author Shuttle on 16/01/20.
 */

public interface Controller<T> {

    T add();

    T add(String s);

    void remove(T t);

    void update();

    void select(T t);

    List<T> getList();

    void flush();

    ProfileDAO getActive();
}