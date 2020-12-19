package com.example.duoihinhbatchu.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duoihinhbatchu.ChoiGameActivity;
import com.example.duoihinhbatchu.DataBase;
import com.example.duoihinhbatchu.KetThucActivity;
import com.example.duoihinhbatchu.object.CauDo;
import com.example.duoihinhbatchu.object.NguoiDung;

import java.util.ArrayList;

public class ChoiGameModel {

    Activity c;
    ArrayList<CauDo> arr = new ArrayList<>();
    int CauSo = -1;
    NguoiDung nguoiDung;
    int MaCD;

    String DATABASE_NAME = "DuoiHinhBatChu.db";
    SQLiteDatabase database;

    public ChoiGameModel(Activity c, int MaCD) {
        this.c = c;
        nguoiDung = new NguoiDung();
        taoData(MaCD);
        this.MaCD=MaCD;
    }

    private void taoData(int MaCD) {
        //Insert Dữ liệu offline
//        AddData();
        //lấy về dữ liệu của chủ đề từ SQLite
        ReadData(MaCD);
//        Log.d("COZZ_4", arr.size()+"_zzzzzzzzzz");
//        arr=new ArrayList<>(DATA.getData().arrCauDo);

//        arr.add(new CauDo("Màn 1","baola","https://1.bp.blogspot.com/-AQYGAAPvMvE/U8jcVMz8ClI/AAAAAAAACyg/8LR7DOjs3bk/s1600/2014-07-17+23.37.47-1.png"));
//        arr.add(new CauDo("Màn 2","obama","https://lazi.vn/uploads/dhbc/1514479758_bc2.jpg"));
//        arr.add(new CauDo("Màn 3","yeuot","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcReFBIaFtgdlxbjZKL8T6vS9JUporAp0nRKEA&usqp=CAU"));
//        arr.add(new CauDo("Màn 4","yeutim","https://files.vforum.vn/2014/T10/img/vforum.vn-133676-10404432-755471441171774-684339299231733906-n.jpg"));
//        arr.add(new CauDo("Màn 5","kedon","https://vcdn-vnexpress.vnecdn.net/2015/06/01/3-4064-1433124775.jpg"));
    }

    private void ReadData(int MaCD) {
        database = DataBase.initDatabase(c, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM CauHoi", null);
        Log.d("COZZ_4", cursor.getCount() + "+++++++");
        arr.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            String ten, dapan, DapAnCoDau;
            int Level, TrangThai, macd;
            byte[] Anh_2;

            ten = cursor.getString(0);
            dapan = cursor.getString(1);
            Anh_2 = cursor.getBlob(2);
            Level = cursor.getInt(3);
            DapAnCoDau = cursor.getString(4);
            TrangThai = cursor.getInt(5);
            macd = cursor.getInt(6);

            if (MaCD == macd) {
                arr.add(new CauDo(ten, dapan, Level, DapAnCoDau, TrangThai, macd, Anh_2));
                Log.d("COZZ_4", Anh_2.length + "+++++++");
            }
        }
        if (arr.size()<=0){
            Intent it = new Intent(c, KetThucActivity.class);
            c.startActivity(it);
            c.finish();
        }
    }

    public CauDo layCauDo() {
        nguoiDung.maCD=MaCD;
        nguoiDung.getTT_Cau(c);
        CauSo=nguoiDung.SoCau;
//        CauSo=0;

        CauDo cd = null;
        cd = arr.get(CauSo);

//        if (CauSo >= arr.size()) {
//            CauSo = arr.size() - 1;
//            Intent it = new Intent(c, KetThucActivity.class);
//            c.startActivity(it);
//            c.finish();
//        }
        return cd;
    }

    public void layThongTin() {
        nguoiDung.getTT_Tien(c);
        nguoiDung.getTT_Cau(c);
    }

    public void luuThongTin() {
        nguoiDung.saveTT(c);
        nguoiDung.saveTT_Cau(c);
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

}
