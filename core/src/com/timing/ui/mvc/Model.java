package com.timing.ui.mvc;

import java.util.List;

/**
 * @author Shuttle on 16/01/20.
 */

public interface Model<T> {

    T addEmpty();

    void addDummy();

    void write();

    void setEmpty(T t);

    void remove(T t);

    void dispose();

    void flush();

    void activate(T t);

    T getActive();

    List<T> getList();
}
