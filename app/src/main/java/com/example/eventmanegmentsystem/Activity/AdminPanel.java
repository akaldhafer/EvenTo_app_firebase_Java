package com.example.eventmanegmentsystem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eventmanegmentsystem.R;
import com.example.eventmanegmentsystem.login.LoginPage;

// extend Activity extends would allow method inheritance
// it allow the subclass to inherit puplic and protected methods and variable
// of the super class and allows you to override the method of you choose to.
public class AdminPanel extends Activity {

    //Overriding is a feature that allows a subclass or child class to provide a specific
    //implementation of a method that is already provided by one of its super-classes or parent classes.
    //Overrides the onCreate method to set the content view to activity_admin_panel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
    }
    //The event handler is being called from the onClick Mehtod that will listen to this function
    public void onViewCustomerCardViewClick(View view) {
        Intent intent = new Intent(AdminPanel.this, Customer_list.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //The event handler is being called from the onClick Method that will listen to this function
    public void onManageEventCardViewClick(View view) {
        Intent intent = new Intent(AdminPanel.this, ManageEventRequests.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void onViewEventsCardViewClickAdmin(View view) {
        Intent intent = new Intent(AdminPanel.this, Event_list.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminPanel.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onLogoutAdmin(View view) {
        Intent intent = new Intent(AdminPanel.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
