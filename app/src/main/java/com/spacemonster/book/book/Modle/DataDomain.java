package com.spacemonster.book.book.Modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataDomain {
    @SerializedName("seatList") public List<Mylist> seatList;
    public List<Mylist> getSeatList(){
        return seatList;
    }
    public class Mylist{
        @SerializedName("list_list_Num") public String list_Num;
        @SerializedName("list_ID") public String list_ID;
        @SerializedName("list_space") public String list_space;
        @SerializedName("list_seatNum") public String list_seatNum;
        @SerializedName("list_date") public String list_date;
        @SerializedName("list_in_out") public String list_in_out;

        public String getList_Num() {
            return list_Num;
        }

        public String getList_ID() {
            return list_ID;
        }

        public String getList_space() {
            return list_space;
        }

        public String getList_seatNum() {
            return list_seatNum;
        }

        public String getList_date() {
            return list_date;
        }

        public String getList_in_out() {
            return list_in_out;
        }
    }

}
