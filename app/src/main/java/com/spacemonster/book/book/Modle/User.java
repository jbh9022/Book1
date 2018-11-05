package com.spacemonster.book.book.Modle;

public class User {
    String ID;
    String PASSWORD;

    public User(String ID, String PASSWORD) {
        this.ID = ID;
        this.PASSWORD = PASSWORD;
    }

    public String getID() {
        return ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
