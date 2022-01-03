package com.ahhasc.Controller;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Payment;
import com.ahhasc.Model.Repository;
import com.ahhasc.Model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PaymentController implements IController{

    private Repository _repository;
    private ArrayList<Payment> _data = new ArrayList<Payment>();

    public PaymentController(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String paymentRecord: _repository.GetRecords()){
            String[] record = paymentRecord.split(";");
            Payment payment = new Payment(Integer.parseInt(record[0]));
            payment.Amount = Float.parseFloat(record[1]);
            payment.DueDate = LocalDate.parse(record[2], DataAccess.DefaultDateFormat);
            payment.IsResolved = Boolean.parseBoolean(record[3]);
            _data.add(payment);
        }
    }

    public ArrayList<Payment> GetPayments(){
        return this._data;
    }

    public Payment GetPayment(int paymentID){
        for (Payment payment: this.GetPayments()){
            if (payment.getID() == paymentID){
                return payment;
            }
        }
        return null;
    }

    public int AddPayment(Payment paymentDescriptor){
        int paymentID = GetNewPaymentID();

        paymentDescriptor.setID(paymentID);

        _data.add(paymentDescriptor);
        _repository.AddNewRecord(paymentDescriptor.toString());
        return paymentID;
    }

    public void UpdatePayment(Payment paymentDescriptor){
        int dataIndex = this.GetPaymentIndex(paymentDescriptor.getID());
        _repository.UpdateRecord(dataIndex,paymentDescriptor.toString());
        _data.set(dataIndex,paymentDescriptor);
    }

    public void DeletePayment(int paymentID){
        int dataIndex = this.GetPaymentIndex(paymentID);
        _repository.DeleteRecord(dataIndex);
        _data.remove(dataIndex);
    }

    private int GetPaymentIndex(int paymentID){
        for (Payment payment: this.GetPayments()){
            if (payment.getID() == paymentID){
                return _data.indexOf(payment);
            }
        }
        return -1;
    }

    private int GetNewPaymentID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(payment -> ids.add(payment.getID()));
        return (Collections.max(ids) + 1);
    }


}
