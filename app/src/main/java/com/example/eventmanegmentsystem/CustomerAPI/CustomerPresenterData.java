package com.example.eventmanegmentsystem.CustomerAPI;

import android.app.Activity;

public interface CustomerPresenterData {

    void onSuccessUpdate(Activity activity, String student_Name, String student_Email,
                         String student_Tp, String student_Pass,
                         String student_DegreeField, String student_PhoneNumber,
                         String token);


}
