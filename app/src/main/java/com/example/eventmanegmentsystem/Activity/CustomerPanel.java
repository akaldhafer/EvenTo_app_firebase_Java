package com.example.eventmanegmentsystem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eventmanegmentsystem.R;
import com.example.eventmanegmentsystem.login.LoginPage;

public class CustomerPanel extends Activity {

    public String stuTP,stuDegreeField,stuName, stuEmail, stuPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);
        // fetching the data from the login page
        stuTP = getIntent().getStringExtra("Tp");
        stuDegreeField = getIntent().getStringExtra("Degree");
        stuName = getIntent().getStringExtra("Name");
        stuEmail= getIntent().getStringExtra("Email");
        stuPhoneNumber = getIntent().getStringExtra("PhoneNo");
    }

    public void onCreateEventCardViewClick(View view) {
        Intent intent = new Intent(CustomerPanel.this, Add_event.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Tp", stuTP);
        intent.putExtra("Degree", stuDegreeField);
        intent.putExtra("Name",stuName);
        intent.putExtra("Email", stuEmail);
        intent.putExtra("PhoneNo", stuPhoneNumber);
        startActivity(intent);
        finish();
    }

    public void onViewCreatedEventCardViewClick(View view) {
        Intent intent = new Intent(CustomerPanel.this, CustomerEventList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Tp", stuTP);
        intent.putExtra("Degree", stuDegreeField);
        intent.putExtra("Name",stuName);
        intent.putExtra("Email", stuEmail);
        intent.putExtra("PhoneNo", stuPhoneNumber);
        startActivity(intent);
        finish();
    }

    public void onEventDashboardCardViewClick(View view) {
        Intent intent = new Intent(CustomerPanel.this, EventDashborad.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Tp", stuTP);
        intent.putExtra("Degree", stuDegreeField);
        intent.putExtra("Name",stuName);
        intent.putExtra("Email", stuEmail);
        intent.putExtra("PhoneNo", stuPhoneNumber);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomerPanel.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onLogoutCustomer(View view) {
        Intent intent = new Intent(CustomerPanel.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
