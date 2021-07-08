package com.example.eventmanegmentsystem.EventAPI;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class EventUploadInfo implements EventPresenter, EventViewMessage {
    private EventViewMessage eventViewMessage;
    private static final String TAG = "EventImp";


    public EventUploadInfo(EventViewMessage eventViewMessage) {
        this.eventViewMessage = eventViewMessage;
    }

    @Override
    public void onUpdateSuccess(String message) {
        eventViewMessage.onUpdateSuccess(message);
    }

    @Override
    public void onUpdateFailure(String message) {
        eventViewMessage.onUpdateFailure(message);
    }

    // taken from Event presenter
    @Override
    public void onSuccessUpdate(Activity activity,String _studentID, String _studentEmail,  String _eventID,String _eventCategory,
                                String _eventDescription, String _eventTime, String _eventDate,
                                String _eventPriceTicket, String _eventImportPicture, String _Token, String _eventStatus)
    {
        EventModule events = new EventModule(_studentID, _studentEmail, _eventID,_eventCategory,
                _eventDescription, _eventTime, _eventDate, _eventPriceTicket, _eventImportPicture, _Token, _eventStatus);
        FirebaseFirestore.getInstance().collection("EventsData").document(_Token)
                .set(events, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            eventViewMessage.onUpdateSuccess("Events has been added ");
                        }else {
                            eventViewMessage.onUpdateFailure("Something went wrong");
                        }
                    }
                });
    }



}
