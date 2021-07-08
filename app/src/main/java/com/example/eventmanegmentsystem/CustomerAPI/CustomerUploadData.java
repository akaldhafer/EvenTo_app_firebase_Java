package com.example.eventmanegmentsystem.CustomerAPI;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class CustomerUploadData implements CustomerPresenterData, CustomerViewMessage {
    private CustomerViewMessage customerViewMessage;
    private static final String TAG = "StudentImp";


    public CustomerUploadData(CustomerViewMessage customerViewMessage) {
        this.customerViewMessage = customerViewMessage;
    }

    @Override
    public void onUpdateSuccess(String message) {
        customerViewMessage.onUpdateSuccess(message);
    }

    @Override
    public void onUpdateFailure(String message) {
        customerViewMessage.onUpdateFailure(message);
    }

    @Override
    public void onSuccessUpdate(Activity activity, String student_Name, String student_Email,
                                String student_Tp, String student_Pass,
                                String student_DegreeField, String student_PhoneNumber,
                                String token)
    {
        CustomerModule users = new CustomerModule(student_Name, student_Email,
                student_Tp, student_Pass, student_DegreeField, student_PhoneNumber,
                token);
        FirebaseFirestore.getInstance().collection("StudentData").document(token)
                .set(users, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            customerViewMessage.onUpdateSuccess("Student has been added ");
                        }else {
                            customerViewMessage.onUpdateFailure("Something went wrong");
                        }
                    }
                });
    }



}
