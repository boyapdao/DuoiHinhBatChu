package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.duoihinhbatchu.api.GetCauHoi;

public class MainActivity extends AppCompatActivity {

    Button btn_Choi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetCauHoi().execute();

        Init();
        Events();
    }

    private void Events() {
        btn_Choi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DATA.getData().arrCauDo.size()>0){
                    Intent it=new Intent(MainActivity.this, ChoiGameActivity.class);
                    startActivity(it);
                }
                else {
                    Toast.makeText(MainActivity.this, "Rớt mạng rồi Đạik", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Init() {
        btn_Choi=findViewById(R.id.btn_Choi);
    }
}