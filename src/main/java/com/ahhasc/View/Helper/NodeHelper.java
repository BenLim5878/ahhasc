package com.ahhasc.View.Helper;

import com.ahhasc.ResourceLoader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public static void SetDropShadow(Node target, double offSetX, double offSetY){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(offSetX);
        dropShadow.setOffsetY(offSetY);
        dropShadow.setColor(Color.rgb(0,0,0,0.25));
        target.setEffect(dropShadow);
    }

    public static void SetTextfieldDigitOnly(TextField textField){
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

    public static String[] ProcessTime(LocalDateTime date){
        LocalTime time = date.toLocalTime();
        String[] out = new String[3];
        out[0] = String.format("%d",time.getHour());
        if (time.getHour() >= 12){
            out[2] = "PM";
            if (time.getHour() > 12){
                out[0] = String.format("%d",(time.getHour() -12));
            }
        } else {
            out[2] = "AM";
        }

        if (time.getMinute() == 0){
            out[1] = "00";
        } else {
            out[1] = String.format("%d",time.getMinute());
        }
        return out;
    }

    public static Node LoadNode(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        return loader.load();
    }
}