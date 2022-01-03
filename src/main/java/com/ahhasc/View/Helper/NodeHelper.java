package com.ahhasc.View.Helper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
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

}