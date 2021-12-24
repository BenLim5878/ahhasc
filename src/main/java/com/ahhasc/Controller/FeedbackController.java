package com.ahhasc.Controller;

import com.ahhasc.Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class FeedbackController implements IController{

    private Repository _repository;
    private ArrayList<Feedback> _data = new ArrayList<Feedback>();

    public FeedbackController(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String feedbackRecord : _repository.GetRecords()){
            String[] record = feedbackRecord.split(";");
            Feedback feedback = new Feedback(Integer.parseInt(record[0]));
            feedback.setRating(Float.parseFloat(record[1]));
            feedback.setDescription(record[2]);
            feedback.CreatedAt = LocalDateTime.parse(record[3],DataAccess.DefaultDateTimeFormat);
            _data.add(feedback);
        }
    }

    public Feedback GetFeedback(int feedbackID){
        for (Feedback feedback:_data){
            if (feedback.getID() == feedbackID){
                return feedback;
            }
        }
        return null;
    }

   public void AddFeedback(Feedback feedbackDescriptor){
       int paymentID = GetNewFeedbackID();

       feedbackDescriptor.setID(paymentID);

       _data.add(feedbackDescriptor);
       _repository.AddNewRecord(feedbackDescriptor.toString());
   }

   public void UpdateFeedback(Feedback feedbackDescriptor){
       int dataIndex = this.GetFeedbackIndex(feedbackDescriptor.getID());
       _repository.UpdateRecord(dataIndex,feedbackDescriptor.toString());
       _data.set(dataIndex,feedbackDescriptor);
   }

   public void DeleteFeedback(int feedbackID){
       int dataIndex = this.GetFeedbackIndex(feedbackID);
       _repository.DeleteRecord(dataIndex);
       _data.remove(dataIndex);
   }

    private int GetFeedbackIndex(int feedbackIndex){
        for (Feedback feedback: _data){
            if (feedback.getID() == feedbackIndex){
                return _data.indexOf(feedback);
            }
        }
        return -1;
    }

    private int GetNewFeedbackID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(feedback -> ids.add(feedback.getID()));
        return (Collections.max(ids) + 1);
    }
}
