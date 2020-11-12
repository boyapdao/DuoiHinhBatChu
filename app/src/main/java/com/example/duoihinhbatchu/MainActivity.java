package com.example.duoihinhbatchu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.api.GetCauHoi;

public class MainActivity extends AppCompatActivity {

    Button btn_Choi;
    ImageView img_sound, img_Rate, img_logo;
    TextView tv_sound;
    int i_sound=1;

    Animation anim_rotate, anim_scale;
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
        img_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i_sound==1){
                    img_sound.setImageResource(R.drawable.sound_off);
                    tv_sound.setText("Sound Off");
                    i_sound=2;
                }
                else {
                    img_sound.setImageResource(R.drawable.sound_on);
                    tv_sound.setText("Sound On");
                    i_sound=1;
                }
            }
        });
    }

    private void Init() {
        btn_Choi=findViewById(R.id.btn_Choi);
        img_sound=findViewById(R.id.img_sound);
        img_Rate=findViewById(R.id.img_rate);
        img_logo=findViewById(R.id.logo);
        tv_sound=findViewById(R.id.tv_Sound);

        anim_rotate= AnimationUtils.loadAnimation(this, R.anim.rotate);
        anim_scale=AnimationUtils.loadAnimation(this, R.anim.scale);

        img_Rate.startAnimation(anim_rotate);
        img_logo.startAnimation(anim_scale);

    }
}