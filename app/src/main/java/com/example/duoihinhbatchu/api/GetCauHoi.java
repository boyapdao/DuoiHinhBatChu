package com.example.duoihinhbatchu.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.duoihinhbatchu.DATA;
import com.example.duoihinhbatchu.object.CauDo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetCauHoi extends AsyncTask<Void, Void, Void> {

    String data;
    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client=new OkHttpClient();//gọi đến api để lấy dữ liệu
        Request request=new Request.Builder()
                .url("https://khueng.000webhostapp.com/getCauHoi.php")//Khai báo địa chỉ api
                .build();
        Response response=null;//Nhận về thông qua response

        try {
            response=client.newCall(request).execute();
            ResponseBody responseBody=response.body();//Lấy kết quả trả ra
            data=responseBody.string();//Gán vào cho data

        } catch (IOException e) {
            data=null;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data!=null){
            try {
                DATA.getData().arrCauDo.clear();
                JSONArray array=new JSONArray(data);
                for(int i=0;i<array.length();i++){
                    JSONObject o=array.getJSONObject(i);
                    CauDo c=new CauDo();
                    c.setTen(o.getString("Ten"));
                    c.setDapAn(o.getString("DapAn"));
                    c.setAnh(o.getString("Anh"));
                    c.setLevel(o.getInt("Level"));
                    c.setDapAnCoDau(o.getString("DapAnCoDau"));
                    c.setTrangThai(o.getInt("TrangThai"));
                    c.setMaCD(o.getInt("MaCD"));

                    DATA.getData().arrCauDo.add(c);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
