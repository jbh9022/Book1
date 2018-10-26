package com.spacemonster.book.book.Modle;

public class Post {
    String list_Num;
    String list_ID;
    String list_space;
    String list_seatNum;
    String list_date;
    String list_date2;
    String list_in_out;

    public Post(String list_Num, String list_ID, String list_space, String list_seatNum, String list_date, String list_date2, String list_in_out) {
        this.list_Num = list_Num;
        this.list_ID = list_ID;
        this.list_space = list_space;
        this.list_seatNum = list_seatNum;
        this.list_date = list_date;
        this.list_date2 = list_date2;
        this.list_in_out = list_in_out;
    }

    public String getList_Num() {
        return list_Num;
    }

    public void setList_Num(String list_Num) {
        this.list_Num = list_Num;
    }

    public String getList_ID() {
        return list_ID;
    }

    public void setList_ID(String list_ID) {
        this.list_ID = list_ID;
    }

    public String getList_space() {
        return list_space;
    }

    public void setList_space(String list_space) {
        this.list_space = list_space;
    }

    public String getList_seatNum() {
        return list_seatNum;
    }

    public void setList_seatNum(String list_seatNum) {
        this.list_seatNum = list_seatNum;
    }

    public String getList_date() {
        return list_date;
    }

    public void setList_date(String list_date) {
        this.list_date = list_date;
    }

    public String getList_in_out() {
        return list_in_out;
    }

    public void setList_in_out(String list_in_out) {
        this.list_in_out = list_in_out;
    }

    public String getList_date2() {
        return list_date2;
    }
}
