package com.example.eventmanegmentsystem.EventAPI;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EventPullData implements EventPresenterFetchData {

    private Context context;
    private EventViewFetchData eventViewFetchData;
    public EventPullData(Context context, EventViewFetchData eventViewFetchData) {
        this.context = context;
        this.eventViewFetchData = eventViewFetchData;
    }

    @Override
    public void onSuccessUpdate(Activity activity) {
        // the data table for events
        FirebaseFirestore.getInstance().collection("EventsData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        String studentID = task.getResult().getDocuments().get(i).getString("studentID");
                        String studentEmail = task.getResult().getDocuments().get(i).getString("studentEmail");
                        String eventID = task.getResult().getDocuments().get(i).getString("eventID");
                        String eventDescription = task.getResult().getDocuments().get(i).getString("eventDescription");
                        String eventCategory = task.getResult().getDocuments().get(i).getString("eventCategory");
                        String eventTime = task.getResult().getDocuments().get(i).getString("eventTime");
                        String eventDate = task.getResult().getDocuments().get(i).getString("eventDate");
                        String eventPriceTicket = task.getResult().getDocuments().get(i).getString("eventPriceTicket");
                        String eventImportPicture = task.getResult().getDocuments().get(i).getString("eventImportPicture");
                        String Token = task.getResult().getDocuments().get(i).getString("token").trim();
                        String eventStatus = task.getResult().getDocuments().get(i).getString("eventStatus");
                        EventModule eventModules = new EventModule();
                        eventModules.setStudentID(studentID);
                        eventModules.setStudentEmail(studentEmail);
                        eventModules.setEventID(eventID);
                        eventModules.setEventDescription(eventDescription);
                        eventModules.setEventCategory(eventCategory);
                        eventModules.setEventTime(eventTime);
                        eventModules.setEventDate(eventDate);
                        eventModules.setEventPriceTicket(eventPriceTicket);
                        eventModules.setEventImportPicture(eventImportPicture);
                        eventModules.setToken(Token);
                        eventModules.setEventStatus(eventStatus);
                        eventViewFetchData.onUpdateSuccess(eventModules);

                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                eventViewFetchData.onUpdateFailure(e.getMessage().toString());
            }
        });

    }


}
