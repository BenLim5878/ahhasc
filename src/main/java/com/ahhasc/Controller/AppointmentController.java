package com.ahhasc.Controller;

import com.ahhasc.Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class AppointmentController implements IController {

    private Repository _repository;
    private ArrayList<Appointment> _data = new ArrayList<Appointment>();

    public AppointmentController(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String appointmentRecord: _repository.GetRecords()){
            String[] record = appointmentRecord.split(";");
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(Integer.parseInt(record[0]));
            appointment.StartTime = LocalDateTime.parse(record[1], DataAccess.DefaultDateTimeFormat);
            appointment.BookingCustomer = new Customer(Integer.parseInt(record[2]));
            appointment.ActiveManager = new Manager(Integer.parseInt(record[3]));
            String[] techniciansIds = record[4].split(",");
            for (int i = 0; i < techniciansIds.length;i++){
                int id = Integer.parseInt(techniciansIds[i]);
                appointment.ActiveTechnicians.add(new Technician(id));
            }
            appointment.Payment = new Payment(Integer.parseInt(record[5]));
            appointment.setIsCompleted(Boolean.parseBoolean(record[6]));
            appointment.setFeedback(new Feedback(Integer.parseInt(record[7])));
            _data.add(appointment);
        }
    }

    public ArrayList<Appointment> GetAppointments(){
        ArrayList<Appointment> out = new ArrayList<Appointment>();
        for (Appointment appointment: this._data){
           out.add(ResolveIds(appointment));
        }
        return out;
    }

    public Appointment GetAppointmentByID(int appointmentID){
        for (Appointment appointment: this.GetAppointments()){
            if (appointment.getAppointmentID() ==  appointmentID){
                return ResolveIds(appointment);
            }
        }
        return null;
    }

    public ArrayList<Appointment> GetAppointmentByManagerID(int managerID, boolean isActive){
        ArrayList<Appointment> out = new ArrayList<Appointment>();
        for (Appointment appointment: this.GetAppointments()){
            if (appointment.ActiveManager.getManagerID() == managerID){
                if (isActive){
                    if (!appointment.getIsCompleted()){
                        out.add(ResolveIds(appointment));
                    }
                } else {
                    out.add(ResolveIds(appointment));
                }
            }
        }
        return out;
    }

    public ArrayList<Appointment> GetAppointmentByTechnicianID(int technicianID, boolean isActive){
        ArrayList<Appointment> out = new ArrayList<Appointment>();
        for (Appointment appointment: this.GetAppointments()){
            for (int id : appointment.GetActiveTechnicianIds()){
                if (id == technicianID){
                    if (isActive){
                        if (!appointment.getIsCompleted()){
                            out.add(ResolveIds(appointment));
                        }
                    } else {
                        out.add(ResolveIds(appointment));
                    }
                }
            }
        }
        return out;
    }

    public void AddAppointment(Appointment appointmentDescriptor){
        int appointmentID = GetNewAppointmentID();

        appointmentDescriptor.setAppointmentID(appointmentID);

        _data.add(appointmentDescriptor);
        _repository.AddNewRecord(appointmentDescriptor.toString());
    }

    public void UpdateAppointment(Appointment appointmentDescriptor){
        int dataIndex = this.GetAppointmentIndex(appointmentDescriptor.getAppointmentID());
        _repository.UpdateRecord(dataIndex,appointmentDescriptor.toString());
        _data.set(dataIndex, appointmentDescriptor);
    }

    public void DeleteAppointment(int appointmentID){
        int dataIndex = this.GetAppointmentIndex(appointmentID);
        _repository.DeleteRecord(dataIndex);
        _data.remove(dataIndex);
    }

    private int GetAppointmentIndex(int appointmentID){
        for (Appointment appointment: this.GetAppointments()){
            if (appointment.getAppointmentID() == appointmentID){
                return _data.indexOf(appointment);
            }
        }
        return -1;
    }

    private int GetNewAppointmentID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(appointment -> ids.add(appointment.getAppointmentID()));
        return (Collections.max(ids) + 1);
    }

    private static Appointment ResolveIds(Appointment appointment){
        // Customer
        appointment.BookingCustomer = DataAccess.GetInstance().CustomerController.GetCustomer(new Customer(appointment.BookingCustomer.getCustomerID())).get(0);
        // Manager
        appointment.ActiveManager = (Manager)DataAccess.GetInstance().UserController.GetManager(appointment.ActiveManager.getManagerID());
        // Technicians
        for (Technician technician: appointment.ActiveTechnicians){
            int index = appointment.ActiveTechnicians.indexOf(technician);
            Technician t = DataAccess.GetInstance().UserController.GetTechnician(technician.getTechnicianID());
            appointment.ActiveTechnicians.set(index, t);
        }
        // Payment
        appointment.Payment = DataAccess.GetInstance().PaymentController.GetPayment(appointment.Payment.getID());
        // Feedback
        appointment.setFeedback(DataAccess.GetInstance().FeedbackController.GetFeedback(appointment.getFeedback().getID()));

        return appointment;
    }
}
