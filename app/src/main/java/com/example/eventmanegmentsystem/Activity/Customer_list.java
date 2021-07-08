package com.example.eventmanegmentsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanegmentsystem.Adapters.CustomerListAdapter;
import com.example.eventmanegmentsystem.CustomerAPI.CustomerFetchData;
import com.example.eventmanegmentsystem.CustomerAPI.CustomerModule;
import com.example.eventmanegmentsystem.CustomerAPI.CustomerViewFetchData;
import com.example.eventmanegmentsystem.R;

import java.util.ArrayList;


public class Customer_list extends AppCompatActivity implements CustomerViewFetchData {
    private RecyclerView ListDataView;
    private CustomerListAdapter mPostsAdapter;
    ArrayList<CustomerModule> studentArrayList = new ArrayList<>();
    private CustomerFetchData studentPullData;
    private static final String TAG = "StudentList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ListDataView = findViewById(R.id.StudentListView);
        studentPullData = new CustomerFetchData(this,this);
        RecyclerViewMethods();
        studentPullData.onSuccessUpdate(this);
    }

    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        mPostsAdapter = new CustomerListAdapter(this, studentArrayList);
        ListDataView.setAdapter(mPostsAdapter);
        ListDataView.invalidate();

    }

// we fetch the data from the student pull data then we set it into the student module then we add
// each module into the jobpostlist
    @Override
    public void onUpdateSuccess(CustomerModule message) {
        if(message != null){
            CustomerModule customerModules = new CustomerModule();
            customerModules.setStudent_Name(message.getStudent_Name());
            customerModules.setStudent_PhoneNumber(message.getStudent_PhoneNumber());
            customerModules.setStudent_Email(message.getStudent_Email());
            customerModules.setStudent_Tp(message.getStudent_Tp());
            customerModules.setStudent_Pass(message.getStudent_Pass());
            customerModules.setStudent_DegreeField(message.getStudent_DegreeField());
            studentArrayList.add(customerModules);

        }
        mPostsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onUpdateFailure(String message) {

        Toast.makeText(Customer_list.this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Customer_list.this, AdminPanel.class);
        startActivity(intent);
        finish();
    }
}