package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.duoihinhbatchu.adapter.DapAnAdapter;

import java.util.ArrayList;

public class ChoiGameActivity extends AppCompatActivity {


    ArrayList<String> arrCauTraLoi;
    GridView gdvCauTraLoi;

    ArrayList<String> arrDapAn;
    GridView gdvDapAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);
        Init();
        ReadData();
        hienCauTraLoi();
        hienDapAn();
    }

    private void ReadData() {
        arrCauTraLoi=new ArrayList<>();
        arrCauTraLoi.add("B");
        arrCauTraLoi.add("B");
        arrCauTraLoi.add("B");
        arrCauTraLoi.add("B");
        arrCauTraLoi.add("B");

        arrDapAn=new ArrayList<>();
        arrDapAn.add("D");
        arrDapAn.add("K");
        arrDapAn.add("E");
        arrDapAn.add("H");
        arrDapAn.add("D");
        arrDapAn.add("Q");
        arrDapAn.add("L");
        arrDapAn.add("P");
    }

    private void Init() {
        gdvCauTraLoi=findViewById(R.id.gdvCauTraLoi);
        gdvDapAn=findViewById(R.id.gdvDapAn);

    }

    private void hienCauTraLoi(){//đẩy câu trả lời lên màn hình
        gdvCauTraLoi.setNumColumns(arrCauTraLoi.size());
        gdvCauTraLoi.setAdapter(new DapAnAdapter(this,0,arrCauTraLoi));

    }
    private void hienDapAn(){//đẩy đáp án lên màn hình
        gdvDapAn.setNumColumns(arrDapAn.size()/2);
        gdvDapAn.setAdapter(new DapAnAdapter(this,0,arrDapAn));

    }
}