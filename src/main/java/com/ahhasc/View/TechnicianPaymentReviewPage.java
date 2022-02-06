package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.AppointmentRowPayment;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnicianPaymentReviewPage implements Initializable {

    @FXML
    private VBox appointmentList, alertContainer;
    @FXML
    private TechnicianMenuLayout layoutController;
    @FXML
    private Button firstTabButton, secondTabButton;
    @FXML
    private Text alertText, alertTitle;

    private static final String COMPLETEDAPPOINTMENTPAYMENT = "Completed Appointment Payment";
    private static final String COMPLETEDAPPOINTMENTFEEDBACK = "Completed Appointment Feedback";
    private static final String  PAYMENTHISTORY= "Payment History";
    private static final String FEEDBACKHISTORY="Feedback History";

    public static final String PAYMENT = "Payment";
    public static final String FEEDBACK = "Feedback";

    private String _interfaceType = PAYMENT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(TechnicianMenuLayout.PAYMENT);
        LoadAppointmentList(getData(COMPLETEDAPPOINTMENTPAYMENT),COMPLETEDAPPOINTMENTPAYMENT);
    }

    public void SetInterface(String interfaceType){
        _interfaceType = interfaceType;
        UpdateInterface();
        switch (interfaceType){
            case PAYMENT -> {
                layoutController.SetTab(TechnicianMenuLayout.PAYMENT);
                LoadAppointmentList(getData(COMPLETEDAPPOINTMENTPAYMENT),COMPLETEDAPPOINTMENTPAYMENT);
            }
            case FEEDBACK -> {
                layoutController.SetTab(TechnicianMenuLayout.FEEDBACK);
                LoadAppointmentList(getData(COMPLETEDAPPOINTMENTFEEDBACK),COMPLETEDAPPOINTMENTFEEDBACK);
            }
        }
    }

    private void UpdateInterface(){
        switch (_interfaceType){
            case PAYMENT -> {
                secondTabButton.setText("Payment History");
            }
            case FEEDBACK -> {
                secondTabButton.setText("Feedback History");
            }
        }
    }

    private ArrayList<Appointment> getData(String appointmentType){
        ArrayList<Appointment> out = new ArrayList<>();
        switch (_interfaceType){
            case PAYMENT -> {
                out = getPaymentData(appointmentType);
            }
            case FEEDBACK -> {
                out = getFeedbackData(appointmentType);
            }
        }
        return out;
    }

    private ArrayList<Appointment> getFeedbackData(String appointmentType){
        ArrayList<Appointment> out = new ArrayList<>();
        ArrayList<Appointment> allAppointments = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(Session.GetInstance().LoggedUser.getID(), false);
        for (Appointment appointment: allAppointments){
            if (appointmentType.equals(FEEDBACKHISTORY)){
                if (appointment.getFeedback() != null){
                    out.add(appointment);
                }
            } else {
                if (appointment.getFeedback() == null){
                    out.add(appointment);
                }
            }
        }
        return out;
    }

    private ArrayList<Appointment> getPaymentData(String appointmentType){
        ArrayList<Appointment> out = new ArrayList<>();
        ArrayList<Appointment> allAppointments = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(Session.GetInstance().LoggedUser.getID(),false);
        for (Appointment appointment:allAppointments){
            switch (appointmentType){
                case COMPLETEDAPPOINTMENTPAYMENT -> {
                    if (!appointment.Payment.IsResolved){
                        out.add(appointment);
                    }
                }
                case PAYMENTHISTORY -> {
                    if (appointment.Payment.IsResolved){
                        out.add(appointment);
                    }
                }
            }
        }
        return out;
    }

    private void LoadAppointmentList(ArrayList<Appointment> appointments, String appointmentType){
        appointmentList.getChildren().clear();
        try{
            for (Appointment appointment: appointments){
                FXMLLoader appointmentRowLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/AppointmentRowPayment.fxml"));
                appointmentList.getChildren().add(appointmentRowLoader.load());
                AppointmentRowPayment appointmentRowController = (AppointmentRowPayment) appointmentRowLoader.getController();
                setAppointmentRowType(appointmentRowController, appointment, appointmentType);
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        NodeHelper.UpdateAppointmentContainer(appointmentList,alertContainer);
    }

    private void setAppointmentRowType(AppointmentRowPayment appointmentRowController, Appointment appointment, String appointmentType){
        switch (_interfaceType){
            case PAYMENT -> {
                if (appointmentType.equals(PAYMENTHISTORY)){
                    appointmentRowController.IsDone(true);
                } else {
                    appointmentRowController.IsDone(false);
                }
                appointmentRowController.setRowType(AppointmentRowPayment.PAYMENT);
            }
            case FEEDBACK -> {
                if (appointmentType.equals(FEEDBACKHISTORY)){
                    appointmentRowController.IsDone(true);
                } else {
                    appointmentRowController.IsDone(false);
                }
                appointmentRowController.setRowType(AppointmentRowPayment.FEEDBACK);
            }
        }
        appointmentRowController.setAlertContainer(alertContainer);
        appointmentRowController.SetAppointment(appointment);
    }

    private void changeTab(String tabType){
        LoadAppointmentList(getData(tabType),tabType);
    }

    @FXML
    private void toHistory(){
        switch (_interfaceType){
            case PAYMENT -> {
                changeTab(PAYMENTHISTORY);
            }
            case FEEDBACK -> {
                changeTab(FEEDBACKHISTORY);
            }
        }
        firstTabButton.setStyle("-fx-background-color:transparent;-fx-text-fill: #808080");
        secondTabButton.setStyle("-fx-background-color:#58ABA5;-fx-text-fill: #fcfcfc");
    }

    @FXML
    private void toCompletedAppointment(){
        switch (_interfaceType){
            case PAYMENT -> {
                changeTab(COMPLETEDAPPOINTMENTPAYMENT);
            }
            case FEEDBACK -> {
                changeTab(COMPLETEDAPPOINTMENTFEEDBACK);
            }
        }
        secondTabButton.setStyle("-fx-background-color:transparent;-fx-text-fill: #808080");
        firstTabButton.setStyle("-fx-background-color:#58ABA5;-fx-text-fill: #fcfcfc");
    }
}
