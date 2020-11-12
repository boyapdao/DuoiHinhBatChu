package com.example.duoihinhbatchu.object;

public class CauDo {
    private String ten, dapAn, anh, level;

    public CauDo(){ }

    public CauDo(String ten, String dapAn, String anh) {
        this.ten = ten;
        this.dapAn = dapAn;
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
