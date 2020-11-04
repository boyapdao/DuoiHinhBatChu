package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchu.adapter.DapAnAdapter;
import com.example.duoihinhbatchu.model.ChoiGameModel;
import com.example.duoihinhbatchu.object.CauDo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class ChoiGameActivity extends AppCompatActivity {

    ChoiGameModel model;
    CauDo cauDo;

    String DapAn = "yeuot";
    int Index = 0;
    ArrayList<String> arrCauTraLoi;
    GridView gdvCauTraLoi;

    ArrayList<String> arrDapAn;
    GridView gdvDapAn;
    ImageView imgAnhCauHoi;
    TextView tv_TienNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);
        Init();
        Events();
        hienCauDo();
    }

    private void Events() {
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0 && Index < arrCauTraLoi.size()) {
                    for (int i = 0; i < arrCauTraLoi.size(); i++) {
                        if (arrCauTraLoi.get(i).length() == 0) {
                            Index = i;
                            break;
                        }
                    }
                    arrDapAn.set(position, "");
                    arrCauTraLoi.set(Index, s);
                    Index++;
                    hienCauTraLoi();
                    hienDapAn();
                    checkWin();
                }
            }
        });

        gdvCauTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0) {
                    Index = position;
                    arrCauTraLoi.set(position, "");
                    for (int i = 0; i < arrDapAn.size(); i++) {
                        if (arrDapAn.get(i).length() == 0) {
                            arrDapAn.set(i, s);
                            break;
                        }
                    }
                    hienCauTraLoi();
                    hienDapAn();
                }
            }
        });
    }

    private void Init() {
        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauHoi = findViewById(R.id.img_anhCauDo);
        tv_TienNguoiDung = findViewById(R.id.tv_tienNguoiDung);

        model = new ChoiGameModel(this);
        arrCauTraLoi = new ArrayList<>();
        arrDapAn = new ArrayList<>();
    }

    private void hienCauDo() {
        cauDo = model.layCauDo();
        DapAn = cauDo.getDapAn();

        bamData();
        hienCauTraLoi();
        hienDapAn();

        Glide.with(this)
                .load(cauDo.getAnh())
                .into(imgAnhCauHoi);
        model.layThongTin();
        tv_TienNguoiDung.setText(model.getNguoiDung().tien + " $");
    }

    private void hienCauTraLoi() {//đẩy câu trả lời lên màn hình
        gdvCauTraLoi.setNumColumns(arrCauTraLoi.size());
        gdvCauTraLoi.setAdapter(new DapAnAdapter(this, 0, arrCauTraLoi));

    }

    private void hienDapAn() {//đẩy đáp án lên màn hình
        gdvDapAn.setNumColumns(arrDapAn.size() / 2);
        gdvDapAn.setAdapter(new DapAnAdapter(this, 0, arrDapAn));
    }

    private void bamData() {
        Index = 0;
        arrCauTraLoi.clear();
        arrDapAn.clear();
        Random r = new Random();
        for (int i = 0; i < DapAn.length(); i++) {
            arrCauTraLoi.add("");

            String s = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s);

            String s1 = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s1);
        }
        for (int i = 0; i < DapAn.length(); i++) {
            String s = "" + DapAn.charAt(i);
            arrDapAn.set(i, s.toUpperCase());
        }
        Collections.shuffle(arrDapAn);// trộn đáp án
//        for(int i=0;i<10;i++){
//            String s=arrDapAn.get(i);
//            int vt=r.nextInt(arrDapAn.size());
//            arrDapAn.set(i,arrDapAn.get(vt));
//            arrDapAn.set(vt,s);
//        }
    }

    private void checkWin() {
        String s = "";
        for (String s1 : arrCauTraLoi) {
            s = s + s1;
        }
        s = s.toUpperCase();
        if (s.equals(DapAn.toUpperCase())) {
            Toast.makeText(this, "Bạn đã chiến thắng", Toast.LENGTH_LONG).show();
            model.layThongTin();
            model.getNguoiDung().tien = model.getNguoiDung().tien + 10;
            model.luuThongTin();
            hienCauDo();
        }
    }

//    public void moGoiY(View view) {
//        int id = -1;
//        if (id == -1) {
//            for (int i = 0; i < arrCauTraLoi.size(); i++) {
//                String s = DapAn.toUpperCase().charAt(i) + "";
//                if (!arrCauTraLoi.get(i).toUpperCase().equals(s)) {
//                    id = i;
//                    break;
//                }
//            }
//            for (int i = 0; i < arrDapAn.size(); i++) {
//                if (arrDapAn.get(i).length() == 0) {
//                    arrDapAn.set(i, arrCauTraLoi.get(id));
//                    break;
//                }
//            }
//
//            String GoiY = "" + DapAn.charAt(id);
//            GoiY = GoiY.toUpperCase();
//            for (int i = 0; i < arrDapAn.size(); i++) {
//                if (GoiY.equals(arrDapAn.get(i))) {
//                    arrDapAn.set(i, "");
//                    break;
//                }
//            }
//            arrCauTraLoi.set(id, GoiY);
//            hienCauTraLoi();
//            hienDapAn();
//            model.luuThongTin();
//            model.getNguoiDung().tien = model.getNguoiDung().tien - 5;
//            model.luuThongTin();
//            tv_TienNguoiDung.setText(model.getNguoiDung().tien + " $");
//        }
//    }
}