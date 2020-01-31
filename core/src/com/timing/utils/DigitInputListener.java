package com.timing.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * @author Shuttle on 15/01/20.
 */

public class DigitInputListener extends InputListener {
    private TextField textField;

    public DigitInputListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        int length = textField.getText().length();
        if (length >= 2 && textField.getText().getBytes()[0] == '0') {
            textField.setText(textField.getText().substring(1));
        }
        if (length == 0) {
            textField.setText("0");
        }
        return super.keyTyped(event, character);
    }
}