package com.example.eventmanegmentsystem.CustomerAPI;

public interface CustomerViewMessage {

    void onUpdateFailure(String message);
    void onUpdateSuccess(String message);

}
