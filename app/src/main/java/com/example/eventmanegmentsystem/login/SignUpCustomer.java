package com.example.eventmanegmentsystem.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.eventmanegmentsystem.CustomerAPI.CustomerUploadData;
import com.example.eventmanegmentsystem.CustomerAPI.CustomerViewMessage;
import com.example.eventmanegmentsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.sql.Timestamp;
import java.util.ArrayList;

// extend is used when i want to take some features from another class
// add student is a sub class and Student View is the super class
//Student View  = Super / SignUpCustomer = sub class
public class SignUpCustomer extends Activity implements CustomerViewMessage {

    // Edit Text is taken from the Design Interface Keyword and the
    // Variables used are used in the design of text fields
    private EditText studentName, studentId, studentPW, studentEmail, studentPhoneNumber;
    private MaterialSpinner spinnderId;
    private String eventInterest;

    // Array list to store the Student ID
    public ArrayList<String> userNameList = new ArrayList<>();
    // Array list to store the Student ID
    public ArrayList<String> emailList = new ArrayList<>();
    // The Tag will show in the build terminal
    private static final String TAG = "AddStudents";
    // Declare the student upload info class as a an empty object for now and later will be assigned
    CustomerUploadData customerUploadData;
    // Constructor is used to share data between multiple classes
    public SignUpCustomer() {
    }

    // The system will start from here by checking the ID first from the firebase
    @Override
    protected void onStart() {
        super.onStart();
        checkID();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)

    // We declare the interface field
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Links the SignUpCustomer XML File
        setContentView(R.layout.signup_customer);
        studentName = findViewById(R.id.EditTxtStudentName);
        studentId = findViewById(R.id.EditTxtStudentTP);
        studentPW = findViewById(R.id.EditTxtStudentPasswd);
        spinnderId= findViewById(R.id.spinnerID);
        spinnderId.setItems("Sport Events", "Workshops and classes", "Sponsorships", "Networking sessions", "Conferences", "VIP experiences");
        spinnderId.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selected " + item, Snackbar.LENGTH_LONG).show();
                eventInterest = item;
            }
        });
        studentPhoneNumber = findViewById(R.id.EditTxtPhoneNumber);
        studentEmail = findViewById(R.id.EditTxtStudentEmail);
        customerUploadData = new CustomerUploadData(this);
    }

    // we get the student ID from the database
    //
    private void checkID(){
        FirebaseFirestore.getInstance().collection("StudentData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(int i = 0; i < task.getResult().size(); i++){
                        String studId= task.getResult().getDocuments().get(i).getString("student_Tp").toString();
                        userNameList.add(studId);
                        String email = task.getResult().getDocuments().get(i).getString("studentEmail");
                        emailList.add(email);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpCustomer.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // the event listen will call the f
    public void onAddStudentBtnClick(View view) {
        checkRegisteredDetailsOfStudent();

    }
    private void initSRegisterStudent( String student_Name, String student_Email,
                             String student_Tp, String student_Pass,
                             String student_DegreeField, String student_PhoneNumber,
                             String token) {
        customerUploadData.onSuccessUpdate(SignUpCustomer.this,student_Name, student_Email,
                student_Tp,student_Pass,
                student_DegreeField, student_PhoneNumber,
                token);


    }

    //Checks if the field is empty or not by ading validation
    private void checkRegisteredDetailsOfStudent() {
        String _Name = studentName.getText().toString().trim();
        String _Username = studentId.getText().toString().trim();
        String _Password = studentPW.getText().toString().trim();
        String _PhoneNumber = studentPhoneNumber.getText().toString().trim();
        String _Email = studentEmail.getText().toString().trim();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!TextUtils.isEmpty(_Name)
                && !TextUtils.isEmpty(_Username)
                && !TextUtils.isEmpty(_Password)
                && !TextUtils.isEmpty(_PhoneNumber)
                && !TextUtils.isEmpty(_Email)
                && !eventInterest.isEmpty()){
            //check id if has been used before
            if (userNameList.contains(_Username)){
                Toast.makeText(SignUpCustomer.this, "The Username has been used before, Please change it", Toast.LENGTH_LONG).show();
            }
            else{
                if(emailList.contains(_Email)){
                    Toast.makeText(SignUpCustomer.this, "The Email has been used before, Please change it", Toast.LENGTH_LONG).show();
                }else{
                    //send record to be saved
                    initSRegisterStudent(_Name, _Email, _Username, _Password,eventInterest, _PhoneNumber, timestamp.toString());
                }
                }
        }else{
            if(TextUtils.isEmpty(_Name)){
                studentName.setError("Student Name is required");
                return;
            }if(TextUtils.isEmpty(_Username)){
                studentId.setError("Student Tp is required");
                return;
            }if (TextUtils.isEmpty(_Password)){
                studentPW.setError("Student password is required");
                return;
            }if (TextUtils.isEmpty(_PhoneNumber)){
                studentPhoneNumber.setError("Student Phone Number is required");
                return;
            }if (TextUtils.isEmpty(_Email)){
                studentEmail.setError("Student Email is required");
                return;
            }

        }
    }

    // Updates the user if the process was successfully done
    @Override
    public void onUpdateSuccess(String message) {
        Toast.makeText(SignUpCustomer.this, "Signed up successfully!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SignUpCustomer.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //Updates the user if the process was failed to happen
    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(SignUpCustomer.this, message, Toast.LENGTH_LONG).show();
    }

    //it takes back the user to the previous page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpCustomer.this, LoginPage.class);
        startActivity(intent);
        finish();
    }



}
