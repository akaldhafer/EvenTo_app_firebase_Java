package com.example.eventmanegmentsystem.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventmanegmentsystem.Activity.AdminPanel;
import com.example.eventmanegmentsystem.Activity.CustomerPanel;
import com.example.eventmanegmentsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginPage extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    Button btnLogin;
    EditText edtEmail, edtPassword;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "LoginPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.EditTxtAdminEmailLogin);
        edtPassword = findViewById(R.id.EditxtAdminPasswordLogin);
        btnLogin = findViewById(R.id.BtnLogin);
        mProgressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog.setMessage("Please wait, Logging in..");
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();
            }
        });

    }

    private void initLogin(String email, String password) {
        //we take the email and password to check if the usr is admin or not
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: Login Success");
                    Toast.makeText(LoginPage.this, "Success Login as Admin", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginPage.this, AdminPanel.class);
                    startActivity(intent);
                    finish();
                }else {
                    CustomerLogin(email,password);
                }
            }
        });
    }

    public void CustomerLogin(String email, String password) {

        FirebaseAuth.getInstance().signOut();
        
        // TODO:teacher Login
        firebaseFirestore.collection("StudentData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean CheckUser = false;
                        if (task.isSuccessful()){
                            for (int i=0; i<task.getResult().getDocuments().size(); i++)
                            {
                                // fetch data
                                String StudentName = task.getResult().getDocuments().get(i).getString("student_Name");
                                String DegreeField = task.getResult().getDocuments().get(i).getString("student_DegreeField");
                                String Password = task.getResult().getDocuments().get(i).getString("student_Pass");
                                String studentTP = task.getResult().getDocuments().get(i).getString("student_Tp");
                                String Email = task.getResult().getDocuments().get(i).getString("student_Email");
                                String PhoneNumber = task.getResult().getDocuments().get(i).getString("student_PhoneNumber");

                                if (Email.equals(email) && Password.equals(password)){
                                    Log.d(TAG, "onComplete:  correct ");
                                    Toast.makeText(LoginPage.this, "Welcome back, "+ StudentName,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginPage.this, CustomerPanel.class);
                                    intent.putExtra("Tp", studentTP);
                                    intent.putExtra("Degree", DegreeField);
                                    intent.putExtra("Name",StudentName);
                                    intent.putExtra("Email", Email);
                                    intent.putExtra("PhoneNo", PhoneNumber);
                                    startActivity(intent);
                                    finish();
                                    CheckUser = true;
                                }

                            }
                            if (CheckUser == false){
                                Toast.makeText(LoginPage.this, "Wrong Email or Password !",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(LoginPage.this, "Error, Check Internet connection",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
    private void checkLoginDetails() {
        if(!TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString())){

            initLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
        }else{
            if(TextUtils.isEmpty(edtEmail.getText().toString())){
                edtEmail.setError("Please enter a valid email");
                return;
            }if(TextUtils.isEmpty(edtPassword.getText().toString())){
                edtPassword.setError("Please enter password");
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void onSignupCustomer(View view) {
        Intent intent = new Intent(LoginPage.this, SignUpCustomer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
