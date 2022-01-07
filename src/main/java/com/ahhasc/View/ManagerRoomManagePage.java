package com.ahhasc.View;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Room;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.ManagerCustomerSideMenu;
import com.ahhasc.View.Component.MenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerRoomManagePage implements Initializable {

    @FXML
    private ScrollPane roomListContainer;
    @FXML
    private TextField searchField, roomUnitField, floorField, blockField;
    @FXML
    private Button addRoomButton, deleteButton, updateButton;
    @FXML
    private ImageView addRoomButtonIcon;
    @FXML
    private MenuLayout menuLayoutController;
    @FXML
    private ManagerCustomerSideMenu sideMenuController;
    @FXML
    private VBox roomForm, listContent;

    private ArrayList<Room> rooms = new ArrayList<>();
    private Room selectedRoom;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLayoutController.SetTab(MenuLayout.CUSTOMER);
        sideMenuController.SetTab(ManagerCustomerSideMenu.MANAGEROOM);
        rooms = DataAccess.GetInstance().RoomController.GetRooms();
        NodeHelper.setTextfieldDigitOnly(searchField);
        NodeHelper.setTextfieldDigitOnly(roomUnitField);
        NodeHelper.setTextfieldDigitOnly(floorField);
        loadRoomContent();
    }

    private void loadRoomContent(){
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(280);
        root.setStyle("-fx-background-color: transparent;");
        root.setSpacing(19.94);
        if (rooms.size() > 0){
            for (Room room: rooms){
                VBox node;
                    if (selectedRoom == room){
                        node = buildRoomPane(room, true);
                    } else {
                        node = buildRoomPane(room, false);
                    }
                root.getChildren().add(node);
            }
        }
        roomListContainer.setContent(root);
    }

    private void loadSelectedRoom(){
        if (selectedRoom != null){
            roomForm.setDisable(false);
            if (selectedRoom.Unit != null && selectedRoom.Floor != null && selectedRoom.Block != null){
                roomUnitField.setText(selectedRoom.Unit.toString());
                floorField.setText(selectedRoom.Floor.toString());
                blockField.setText(selectedRoom.Block);
            } else {
                clearFields();
            }
        } else {
            roomForm.setDisable(true);
            clearFields();
        }
    }

    private VBox buildRoomPane(Room room, boolean isSelected){
        // VBox
        VBox roomPane = new VBox();
        NodeHelper.setDropShadow(roomPane, 0 ,2);
        roomPane.setPadding(new Insets(11.5,15,11.5,15));
        if (isSelected){
            roomPane.setStyle("-fx-background-color: #E0F3DD;-fx-background-radius: 11.5");
        } else{
            roomPane.setStyle("-fx-background-color: #E0E7E0;-fx-background-radius: 11.5");
        }
        roomPane.setSpacing(45);
        roomPane.setCursor(Cursor.HAND);

        Label roomUnitText = buildText(String.format("Room %s",room.Unit.toString()), true);
        Label floorText = buildText(String.format("Floor %s",room.Floor.toString()), false);
        Label blockText = buildText(String.format("Block %s",room.Block.toString()), false);

        roomPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (room == selectedRoom){
                    selectedRoom = null;
                    loadSelectedRoom();
                    loadRoomContent();
                } else {
                    selectedRoom = room;
                    loadSelectedRoom();
                    validateContent();
                    loadRoomContent();
                }
            }
        });

        HBox hBox = new HBox();
        hBox.setSpacing(125);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(roomUnitText,blockText);

        roomPane.getChildren().addAll(hBox,floorText);
        return roomPane;
    }

    private Label buildText(String text, boolean isRoomUnit){
        Label newText = new Label(text);

        if (isRoomUnit){
            newText.setStyle("-fx-text-fill: #4C4C4C;-fx-font-family: Nunito;-fx-font-weight: bold;-fx-font-size: 15.34");
        } else {
            newText.setStyle("-fx-text-fill: #4C4C4C;-fx-font-family: Nunito;-fx-font-weight: normal;-fx-font-size: 13.8");
        }
        return newText;
    }

    private void clearFields(){
        roomUnitField.clear();
        floorField.clear();
        blockField.clear();
    }

    @FXML
    private void onSearchFieldType(){
        String roomNo = searchField.getText();
        if (roomNo.length() == 0){
            rooms = DataAccess.GetInstance().RoomController.GetRooms();
        } else {
            if (DataAccess.IsDigit(roomNo)){
                Room room = new Room();
                room.Unit = Integer.parseInt(roomNo);
                rooms = DataAccess.GetInstance().RoomController.GetRoom(room);
            }
        }
        loadRoomContent();
    }

    @FXML
    private void onAddRoomClick(){
        selectedRoom = new Room();
        loadSelectedRoom();
        loadRoomContent();
        validateContent();
    }

    @FXML
    private void onRoomFieldType(){
        validateContent();
    }

    private void validateContent(){
        if (selectedRoom.getRoomID() == null){
            deleteButton.setDisable(true);
            updateButton.setText("Add");
        } else {
            deleteButton.setDisable(false);
            updateButton.setText("Update");
        }

        String roomUnit = roomUnitField.getText().trim();
        String floor = floorField.getText().trim();
        String block = blockField.getText().trim();

        if (roomUnit.length() > 0 && floor.length() > 0 && block.length() >0){
            updateButton.setDisable(false);
        } else {
            updateButton.setDisable(true);
        }
    }

    @FXML
    private void onDeleteButtonClick(){
        if (selectedRoom.getRoomID() != null){
            DataAccess.GetInstance().RoomController.DeleteRoom(selectedRoom.getRoomID());
            reset();
        }
    }

    private void reset(){
        rooms = DataAccess.GetInstance().RoomController.GetRooms();
        searchField.clear();
        selectedRoom = null;
        loadRoomContent();
        loadSelectedRoom();
    }

    @FXML
    private void onUpdateButtonClick(){
        selectedRoom.Unit = Integer.parseInt(roomUnitField.getText().trim());
        selectedRoom.Floor = Integer.parseInt(floorField.getText().trim());
        selectedRoom.Block = blockField.getText().trim();
        // Update
        if (selectedRoom.getRoomID() != null){
            DataAccess.GetInstance().RoomController.UpdateRoom(selectedRoom);
        }
        // Add
        else {
            DataAccess.GetInstance().RoomController.AddRoom(selectedRoom);
        }
        reset();
    }
}
