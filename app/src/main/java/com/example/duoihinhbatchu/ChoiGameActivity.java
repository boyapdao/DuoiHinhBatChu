package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.duoihinhbatchu.adapter.DapAnAdapter;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class ChoiGameActivity extends AppCompatActivity {

    String DapAn="yeuot";
    int Index=0;
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
        banData();
        hienCauTraLoi();
        hienDapAn();
        Events();
    }

    private void Events() {
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= (String) parent.getItemAtPosition(position);
                if(s.length()!=0 && Index<arrCauTraLoi.size()){
                    if(arrCauTraLoi.get(Index).length()!=0) return;
                    arrDapAn.set(position,"");
                    arrCauTraLoi.set(Index,s);
                    Index++;
                    hienCauTraLoi();
                    hienDapAn();
                }
            }
        });
        gdvCauTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= (String) parent.getItemAtPosition(position);
                if(s.length()!=0){
                    Index=position;
                    arrCauTraLoi.set(position,"");
                    for(int i=0;i<arrDapAn.size();i++){
                        if(arrDapAn.get(i).length()==0){
                            arrDapAn.set(i,s);
                            break;
                        }
                    }
                    hienCauTraLoi();
                    hienDapAn();
                }
            }
        });
    }

    private void ReadData() {
        arrCauTraLoi=new ArrayList<>();
        arrDapAn=new ArrayList<>();
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

    private void banData(){
        arrCauTraLoi.clear();
        Random r=new Random();
        for(int i=0;i<DapAn.length();i++){
            arrCauTraLoi.add("");

            String s=""+(char) (r.nextInt(26)+65);
            arrDapAn.add(s);

            String s1=""+(char) (r.nextInt(26)+65);
            arrDapAn.add(s1);
        }
        for(int i=0;i<DapAn.length();i++){
            String s=""+DapAn.charAt(i);
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
}