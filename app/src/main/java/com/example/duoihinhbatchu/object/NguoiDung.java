package com.example.duoihinhbatchu.object;

import android.content.Context;
import android.content.SharedPreferences;

public class NguoiDung {

    private String nameData="appData", nameData_2="appData_2";
    public int tien;

    public int SoCau;
    public int maCD=-1;

    public void saveTT(Context ct){
        SharedPreferences settings=ct.getSharedPreferences(nameData,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("tien",tien );
        editor.commit();
    }

    public void getTT_Tien(Context ct){
        SharedPreferences settings=ct.getSharedPreferences(nameData,0);
        tien=settings.getInt("tien",20);//
    }

    //lưu trạng thái câu hỏi
    public void saveTT_Cau(Context ct){
        SharedPreferences settings=ct.getSharedPreferences(nameData,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt(maCD+"", SoCau);
        editor.commit();
    }
    public void getTT_Cau(Context ct){
        SharedPreferences settings=ct.getSharedPreferences(nameData,0);
        SoCau=settings.getInt(maCD+"", 0);
    }

}
