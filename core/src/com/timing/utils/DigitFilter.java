package com.timing.utils;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * @author Shuttle on 15/01/20.
 */

public class DigitFilter implements TextField.TextFieldFilter {
    private char[] accepted;

    public DigitFilter() {
        accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    @Override
    public boolean acceptChar(TextField textField, char c) {
        return checkIn(c);
    }

    private boolean checkIn(char c) {
        for (char a : accepted) {
            if (a == c) return true;
        }
        return false;
    }
}