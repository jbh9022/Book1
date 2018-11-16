package com.spacemonster.book.book.Modle;

public class SeatNum {
    private int seatNo;
    private String shopMap;
    private String seatNum;

    public SeatNum(int seatNo, String shopMap, String seatNum) {
        this.seatNo = seatNo;
        this.shopMap = shopMap;
        this.seatNum = seatNum;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public String getShopMap() {
        return shopMap;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public void setShopMap(String shopMap) {
        this.shopMap = shopMap;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }
}
