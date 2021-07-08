package com.example.eventmanegmentsystem.EventAPI;

public class EventModule {
    private String studentID;
    private String studentEmail;
    private String eventID;
    private String eventDescription;
    private String eventCategory;
    private String eventTime;
    private String eventDate;
    private String eventPriceTicket;
    private String eventImportPicture;
    private String Token;
    private String eventStatus;

    public EventModule(String _studentID, String _studentEmail,  String _eventID,String _eventCategory,
                       String _eventDescription, String _eventTime, String _eventDate, String _eventPriceTicket,
                       String _eventImportPicture, String _Token, String _eventStatus) {
        this.studentID = _studentID;
        this.studentEmail = _studentEmail;
        this.eventID = _eventID;
        this.eventDescription = _eventDescription;
        this.eventCategory = _eventCategory;
        this.eventTime = _eventTime;
        this.eventDate = _eventDate;
        this.eventPriceTicket = _eventPriceTicket;
        this.eventImportPicture = _eventImportPicture;
        this.Token = _Token;
        this.eventStatus = _eventStatus;
    }

    // generates getter and setter from the empty constructor
    public EventModule(){

    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setEventPriceTicket(String eventPriceTicket) {
        this.eventPriceTicket = eventPriceTicket;
    }

    public String getEventPriceTicket() {
        return eventPriceTicket;
    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


    public String getEventImportPicture() {
        return eventImportPicture;
    }

    public void setEventImportPicture(String eventImportPicture) {
        this.eventImportPicture = eventImportPicture;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
