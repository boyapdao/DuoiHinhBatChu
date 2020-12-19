package com.example.duoihinhbatchu.object;

public class CauDo {
    private String ten, dapAn, anh;// anh dùng để load dữ liệu online
    private int Level;
    private String DapAnCoDau;
    private int TrangThai;
    private int MaCD;
    private byte[] anh_2;// dùng để load dữ liệu offline

    public CauDo(){ }

    public CauDo(String ten, String dapAn, String anh) {
        this.ten = ten;
        this.dapAn = dapAn;
        this.anh = anh;
    }

    // contrcutor get DL OnLine
    public CauDo(String ten, String dapAn, String anh, int level, String dapAnCoDau, int trangThai, int maCD) {
        this.ten = ten;
        this.dapAn = dapAn;
        this.anh = anh;
        Level = level;
        DapAnCoDau = dapAnCoDau;
        TrangThai = trangThai;
        MaCD = maCD;
    }

    // contructor get DL Offline
    public CauDo(String ten, String dapAn, int level, String dapAnCoDau, int trangThai, int maCD, byte[] anh_2) {
        this.ten = ten;
        this.dapAn = dapAn;
        Level = level;
        DapAnCoDau = dapAnCoDau;
        TrangThai = trangThai;
        MaCD = maCD;
        this.anh_2 = anh_2;
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

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getDapAnCoDau() {
        return DapAnCoDau;
    }

    public void setDapAnCoDau(String dapAnCoDau) {
        DapAnCoDau = dapAnCoDau;
    }

    public int getMaCD() {
        return MaCD;
    }

    public void setMaCD(int maCD) {
        MaCD = maCD;
    }

    public byte[] getAnh_2() {
        return anh_2;
    }

    public void setAnh_2(byte[] anh_2) {
        this.anh_2 = anh_2;
    }
}
