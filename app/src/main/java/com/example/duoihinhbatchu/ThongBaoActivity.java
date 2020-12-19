package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchu.object.NguoiDung;

public class ThongBaoActivity extends AppCompatActivity {

    TextView tv_DapAnCoDau;
    ImageView img_gif;
    Button btn_tieptuc;

    Animation anim_alpha;

    MediaPlayer mediaPlayer;
    String DapAnCoDau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        Intent it=getIntent();
        if (it != null)
        {
            Bundle bundle=it.getExtras();
            if(bundle!=null)
                DapAnCoDau= bundle.getString("DapAnCoDau");
        }
        Init();
        Events();
    }

    private void Events() {
        if(MainActivity.mo_Nhac){
            mediaPlayer = MediaPlayer.create(ThongBaoActivity.this, R.raw.tieng_vo_tay);
            mediaPlayer.start();
        }
        btn_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
    }

    private void Init() {
        tv_DapAnCoDau=findViewById(R.id.tv_DapAnCoDau);

        DapAnCoDau=DapAnCoDau.toUpperCase();
        tv_DapAnCoDau.setText(DapAnCoDau);

        img_gif=findViewById(R.id.img_gif);
        btn_tieptuc=findViewById(R.id.btn_tieptuc);

        anim_alpha= AnimationUtils.loadAnimation(this,R.anim.alpha);
        btn_tieptuc.startAnimation(anim_alpha);

        Glide.with(this)
                .load(R.drawable.tenor)
                .into(img_gif);
    }
}