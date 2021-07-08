package com.example.eventmanegmentsystem.CustomerAPI;

public class CustomerModule {
    // attributes needed for student's info
    private String Student_Name, Student_Email,Student_Tp, Student_Pass, Student_DegreeField, Student_PhoneNumber, Token;

    // Constructor of student class
    public CustomerModule(String student_Name, String student_Email, String student_Tp, String student_Pass, String student_DegreeField, String student_PhoneNumber, String token) {
        this.Student_Name = student_Name;
        this.Student_Email = student_Email;
        this.Student_Tp = student_Tp;
        this.Student_Pass = student_Pass;
        this.Student_DegreeField = student_DegreeField;
        this.Student_PhoneNumber = student_PhoneNumber;
        this.Token = token;
    }

    public CustomerModule() {
    }


    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        this.Student_Name = student_Name;
    }

    public String getStudent_Email() {
        return Student_Email;
    }

    public void setStudent_Email(String student_Email) {
        Student_Email = student_Email;
    }

    public String getStudent_Tp() {
        return Student_Tp;
    }

    public void setStudent_Tp(String student_Tp) {
        Student_Tp = student_Tp;
    }

    public String getStudent_Pass() {
        return Student_Pass;
    }

    public void setStudent_Pass(String student_Pass) {
        Student_Pass = student_Pass;
    }

    public String getStudent_DegreeField() {
        return Student_DegreeField;
    }

    public void setStudent_DegreeField(String student_DegreeField) {
        Student_DegreeField = student_DegreeField;
    }

    public String getStudent_PhoneNumber() {
        return Student_PhoneNumber;
    }

    public void setStudent_PhoneNumber(String student_PhoneNumber) {
        Student_PhoneNumber = student_PhoneNumber;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
