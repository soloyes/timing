package com.timing.ui.mvc;

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
}
