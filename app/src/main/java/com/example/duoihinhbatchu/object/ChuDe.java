package com.example.duoihinhbatchu.object;

public class ChuDe {
    private int MaCD;
    private String TenCD;
    private byte[] Hinh;

    public ChuDe(int maCD, String tenCD, byte[] hinh) {
        MaCD = maCD;
        TenCD = tenCD;
        Hinh = hinh;
    }

    public int getMaCD() {
        return MaCD;
    }

    public void setMaCD(int maCD) {
        MaCD = maCD;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String tenCD) {
        TenCD = tenCD;
    }

    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        Hinh = hinh;
    }
}
