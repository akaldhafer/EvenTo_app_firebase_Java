package com.example.eventmanegmentsystem.EventAPI;

import android.app.Activity;

public interface EventPresenter {

    void onSuccessUpdate(Activity activity,String _studentID, String _studentEmail,  String _eventID, String _eventCategory,
                         String _eventDescription, String _eventTime, String _eventDate, String _eventPriceTicket,
                         String _eventImportPicture, String _Token, String _eventStatus);


}
