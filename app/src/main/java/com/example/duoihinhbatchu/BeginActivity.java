package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.duoihinhbatchu.api.GetCauHoi;
import com.example.duoihinhbatchu.object.CauDo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BeginActivity extends AppCompatActivity {

    byte[] Anh;
    String DATABASE_NAME = "DuoiHinhBatChu.db";
    SQLiteDatabase database;
    int tientrinh=0;
    double SLDL;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        new GetCauHoi().execute();
        new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                AddData();
            }
        }.start();
    }
    public void AddData() {
        Log.d("ANH", DATA.getData().arrCauDo.size()+"----");
        SLDL = DATA.getData().arrCauDo.size();

//        c.deleteDatabase(DATABASE_NAME);

        database = DataBase.initDatabase(BeginActivity.this, DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM CauHoi",null);

        if (cursor.getCount() < SLDL) {
            pd=new ProgressDialog(BeginActivity.this);

            //Tiến hành Update lại toàn bộ dữ liệu
            database.delete("CauHoi", null, null);// Xóa toàn bộ dữ liệu cũ

            for (int i = 0; i < DATA.getData().arrCauDo.size(); i++) {

                final CauDo cd = DATA.getData().arrCauDo.get(i);
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        new GetIMAGE().execute(cd.getAnh());// lưu ảnh offline
                    }
                }.start();
            }
        }else {
            startActivity(new Intent(BeginActivity.this, MainActivity.class));
            finish();
        }

    }

    class GetIMAGE extends AsyncTask<String, Void, byte[]> {// lưu ảnh offline
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .build();
        @Override
        protected byte[] doInBackground(String... strings) {
            Request.Builder builder=new Request.Builder();
            builder.url(strings[0]);

            Request request=builder.build();

            try {
                Response response=okHttpClient.newCall(request).execute();
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
//            Log.d("ANH_3", bytes.length+",,,,,,,,,,,");
            if (bytes.length>0){
                tientrinh=tientrinh+1;

                final CauDo cd = DATA.getData().arrCauDo.get(tientrinh-1);
                Anh=bytes;
                String ten, dapan, DapAnCoDau;
                int Level, TrangThai, MaCD;

                ten = cd.getTen();
                dapan = cd.getDapAn();
                Level = cd.getLevel();
                DapAnCoDau = cd.getDapAnCoDau();
                TrangThai = cd.getTrangThai();
                MaCD = cd.getMaCD();

                ContentValues values = new ContentValues();
                values.put("Ten", ten);
                values.put("DapAn", dapan);
                values.put("Anh", Anh);
                values.put("Level", Level);
                values.put("DapAnCoDau", DapAnCoDau);
                values.put("TrangThai", TrangThai);
                values.put("MaCD", MaCD);

                database.insert("CauHoi", null, values);
                Log.d("ANH_4", "Đã load đc "+tientrinh+" bản ghi");

                double p=(tientrinh/SLDL)*100;
                pd.setMessage("Đang load dữ liệu "+p+" %...");
                pd.show();

                if (p==100) {
                    pd.dismiss();
                    startActivity(new Intent(BeginActivity.this, MainActivity.class));
                    finish();
                }
            }
            super.onPostExecute(bytes);
        }
    }
}