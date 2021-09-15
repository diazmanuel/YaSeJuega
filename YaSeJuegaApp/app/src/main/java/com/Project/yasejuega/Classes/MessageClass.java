package com.Project.yasejuega.Classes;

public class MessageClass {
    private String Name;
    private String Subject;
    private String Message;
    private String Email;

    public MessageClass(String name, String subject, String message, String email) {
        Name = name;
        Subject = subject;
        Message = message;
        Email = email;
    }

    public MessageClass() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
