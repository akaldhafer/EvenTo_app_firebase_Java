package com.example.eventmanegmentsystem.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.eventmanegmentsystem.EventAPI.EventUploadInfo;
import com.example.eventmanegmentsystem.EventAPI.EventViewMessage;
import com.example.eventmanegmentsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.sql.Timestamp;

public class Add_event extends AppCompatActivity implements EventViewMessage {

    private EditText eventCategory, eventDescription, eventTime, eventDate, eventPriceTicket;
    private String stuTP;
    private int sID;
    private String stuDegreeField;
    private String stuName;
    private String stuEmail;
    private String stuPhoneNumber;
    public  String sImageUri, eventID;
    private ImageView imageView;
    public Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private StorageTask uploadtask;
    private static final String TAG = "AddStudents";

    ProgressDialog mProgressDialog;
    EventUploadInfo eventUploadInfo;

    public Add_event() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        eventCategory = findViewById(R.id.EditTxtEventCategory);
        eventDescription = findViewById(R.id.EditTxtEventDescription);
        eventTime = findViewById(R.id.EditTxtEventTime);
        eventDate = findViewById(R.id.EditTxtEventDate);
        eventPriceTicket= findViewById(R.id.EditTxtEventPriceTicket);
        eventUploadInfo = new EventUploadInfo(this);
        imageView = (ImageView) findViewById(R.id.ChosenImage);


        storageReference = FirebaseStorage.getInstance().getReference("EventImage");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait, Sending Event Form..");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Add_event.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_event.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    PickerImage();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        stuTP = getIntent().getStringExtra("Tp");
        stuDegreeField = getIntent().getStringExtra("Degree");
        stuName = getIntent().getStringExtra("Name");
        stuEmail= getIntent().getStringExtra("Email");
        stuPhoneNumber = getIntent().getStringExtra("PhoneNo");
        checkID();
    }

    private void checkID(){
        sID = 0;
        FirebaseFirestore.getInstance().collection("EventsData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(int i = 0; i < task.getResult().size(); i++){
                        sID++;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_event.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void uploadFile(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading the image...");
        pd.show();
        Log.d(TAG, "uploadfile: getLastPathSegment type " + imageUri.getLastPathSegment());
        if(imageUri != null){

            StorageReference fileReference =
                    storageReference.child(imageUri.getLastPathSegment());
            uploadtask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pd.dismiss();
                                    sImageUri = uri.toString();
                                    Log.d(TAG, "uploadFile: url will be upload " + sImageUri);
                                    checkSignUpDetails(sImageUri);
                                    Toast.makeText(Add_event.this, "Image Upload successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add_event.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            double progress = (100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            pd.setMessage("Progress: "+ (int) progress + "%");
                        }
                    });

        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                Log.d(TAG, "onActivityResult: url is " + imageUri);
                Picasso.with(Add_event.this).load(result.getUri()).fit().into(imageView);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, "onActivityResult: " + error.getMessage().toString());
            }
        }
    }
    private void PickerImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(Add_event.this);
    }


    private void initCreateEvent(String _studentID, String _studentEmail,  String _eventID,String _eventCategory,
                                 String _eventDescription, String _eventTime, String _eventDate,
                                 String _eventPriceTicket, String _eventImportPicture, String _Token, String _eventStatus) {
        mProgressDialog.show();
        eventUploadInfo.onSuccessUpdate(Add_event.this, _studentID, _studentEmail, _eventID, _eventCategory, _eventDescription
                , _eventTime, _eventDate, _eventPriceTicket, _eventImportPicture, _Token, _eventStatus);
    }
    private void checkSignUpDetails(String imageuri) {
        sID++;
        eventID = Integer.toString(sID);
        String Status = "pending";
        String Category = eventCategory.getText().toString().trim();
        String Description = eventDescription.getText().toString().trim();
        String Time = eventTime.getText().toString().trim();
        String Date = eventDate.getText().toString().trim();
        String Price = eventPriceTicket.getText().toString().trim();
        Log.d(TAG, "checkdetails: url before upload " + imageuri);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!TextUtils.isEmpty(Category)
                && !TextUtils.isEmpty(Description)
                && !TextUtils.isEmpty(Time)
                && !TextUtils.isEmpty(Date)
                && !TextUtils.isEmpty(Price)){
            initCreateEvent(stuTP, stuEmail, eventID, Category, Description, Time, Date, Price, imageuri, timestamp.toString(), Status);
        }else{
            if(TextUtils.isEmpty(Category)){
                eventCategory.setError("Event Category is required");
                return;
            }if (TextUtils.isEmpty(Description)){
                eventDescription.setError("Event Description is required");
                return;
            }
            if(TextUtils.isEmpty(Time)){
                eventTime.setError("Event Time is required");
                return;
            }if (TextUtils.isEmpty(Date)){
                eventDate.setError("Event date is required");
                return;

            }if (TextUtils.isEmpty(Price)){
                eventPriceTicket.setError("Event Price is required");
                return;
            }
        }
    }

    @Override
    public void onUpdateSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(Add_event.this, "Event has been Created", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Add_event.this, CustomerPanel.class);
        intent.putExtra("Tp", stuTP);
        intent.putExtra("Degree", stuDegreeField);
        intent.putExtra("Name",stuName);
        intent.putExtra("Email", stuEmail);
        intent.putExtra("PhoneNo", stuPhoneNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onUpdateFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(Add_event.this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Add_event.this, CustomerPanel.class);
        intent.putExtra("Tp", stuTP);
        intent.putExtra("Degree", stuDegreeField);
        intent.putExtra("Name",stuName);
        intent.putExtra("Email", stuEmail);
        intent.putExtra("PhoneNo", stuPhoneNumber);
        startActivity(intent);
        finish();
    }

    public void onCreateEventByStuBtnClick(View view) {
        if(uploadtask != null && uploadtask.isInProgress()){
            Toast.makeText(Add_event.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        }
        uploadFile();
    }
}
