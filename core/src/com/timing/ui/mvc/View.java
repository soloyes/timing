package com.timing.ui.mvc;

/**
 * @author Shuttle on 16/01/20.
 */

public interface View<T> {
    void init();

    void setController(Controller<T> controller);
}
