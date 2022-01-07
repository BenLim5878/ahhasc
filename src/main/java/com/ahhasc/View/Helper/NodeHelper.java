package com.ahhasc.View.Helper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class NodeHelper {
    public static void RemoveTextFieldFocus(Node fieldNode, Node noneFieldNode){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        fieldNode.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                noneFieldNode.requestFocus();
                firstTime.setValue(false);
            }
        });
    }

    public static void setDropShadow (Node target, double offSetX, double offSetY){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(offSetX);
        dropShadow.setOffsetY(offSetY);
        dropShadow.setColor(Color.rgb(0,0,0,0.25));
        target.setEffect(dropShadow);
    }

    public static void setTextfieldDigitOnly(TextField textField){
        textField.textProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            textField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                        if (textField.getText().length() > 14) {
                            String s = textField.getText().substring(0, 14);
                            textField.setText(s);
                        }
                    }
                }
        );
    }
}