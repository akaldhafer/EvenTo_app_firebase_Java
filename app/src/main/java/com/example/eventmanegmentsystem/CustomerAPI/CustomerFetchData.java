package com.example.eventmanegmentsystem.CustomerAPI;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CustomerFetchData implements CustomerPresenterFetchData {

    private Context context;
    private CustomerViewFetchData studentView;
    public CustomerFetchData(Context context, CustomerViewFetchData studentView) {
        this.context = context;
        this.studentView = studentView;
    }

    @Override
    public void onSuccessUpdate(Activity activity) {
        FirebaseFirestore.getInstance().collection("StudentData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        String StudentName = task.getResult().getDocuments().get(i).getString("student_Name");
                        String DegreeField = task.getResult().getDocuments().get(i).getString("student_DegreeField");
                        String Password = task.getResult().getDocuments().get(i).getString("student_Pass");
                        String studentTP = task.getResult().getDocuments().get(i).getString("student_Tp");
                        String token = task.getResult().getDocuments().get(i).getString("token");
                        String Email = task.getResult().getDocuments().get(i).getString("student_Email");
                        String PhoneNumber = task.getResult().getDocuments().get(i).getString("student_PhoneNumber");
                        CustomerModule customerModules = new CustomerModule();
                        customerModules.setStudent_Name(StudentName);
                        customerModules.setStudent_DegreeField(DegreeField);
                        customerModules.setStudent_Pass(Password);
                        customerModules.setStudent_Tp(studentTP);
                        customerModules.setToken(token);
                        customerModules.setStudent_Email(Email);
                        customerModules.setStudent_PhoneNumber(PhoneNumber);
                        studentView.onUpdateSuccess(customerModules);

                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                studentView.onUpdateFailure(e.getMessage().toString());
            }
        });

    }


}
