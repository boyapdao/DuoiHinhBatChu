package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.api.GetCauHoi;
import com.example.duoihinhbatchu.object.CauDo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button btn_Choi, btn_BangXepHang;
    ImageView img_sound, img_Rate, img_logo, img_question, img_share, img_luatchoi;
    TextView tv_sound;
    int i_sound=1;
    Animation anim_rotate, anim_scale;

    public static boolean mo_Nhac=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        Events();
    }

    private void Events() {
        btn_Choi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this, ChuDeActivity.class);
                startActivity(it);
                if (DATA.getData().arrCauDo.size()>0){
//                    Intent it=new Intent(MainActivity.this, ChoiGameActivity.class);
//                    startActivity(it);
                }
                else {
                    Toast.makeText(MainActivity.this, "Rớt mạng rồi Đạik", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_BangXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this, BangXepHang.class);
                startActivity(it);
            }
        });
        img_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mo_Nhac){
                    img_sound.setImageResource(R.drawable.sound_off);
                    tv_sound.setText("Sound Off");

                    mo_Nhac=false;
                }
                else {
                    img_sound.setImageResource(R.drawable.sound_on);
                    tv_sound.setText("Sound On");

                    MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this, R.raw.mo_amthanh);
                    mediaPlayer.start();

                    mo_Nhac=true;
                }
            }
        });
        img_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/nguyengiakhue2k/"));
                startActivity(intent);
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayout=findViewById(R.id.linear_main);
                Bitmap bitmap = getScreenshot(relativeLayout.getRootView());

                Uri uri=saveImageToShare(bitmap);
                Toast.makeText(MainActivity.this, "Đã chụp màn hình để chia sẻ", Toast.LENGTH_SHORT).show();

                //share intent
                Intent sIntent=new Intent(Intent.ACTION_SEND);
                sIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sIntent.setType("image/png");
                startActivity(Intent.createChooser(sIntent, "Share Via"));
            }
        });
        img_Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chức năng này đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
        img_luatchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LuatChoiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Init() {
        btn_Choi=findViewById(R.id.btn_Choi);
        btn_BangXepHang=findViewById(R.id.btn_BangXepHang);
        img_sound=findViewById(R.id.img_sound);
        img_Rate=findViewById(R.id.img_rate);
        img_logo=findViewById(R.id.logo);
        tv_sound=findViewById(R.id.tv_Sound);
        img_question=findViewById(R.id.img_question);
        img_share=findViewById(R.id.img_share);
        img_luatchoi=findViewById(R.id.img_luatchoi);

        anim_rotate= AnimationUtils.loadAnimation(this, R.anim.rotate);
        anim_scale=AnimationUtils.loadAnimation(this, R.anim.scale);

        img_Rate.startAnimation(anim_rotate);
        img_logo.startAnimation(anim_scale);

    }

    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    private Uri saveImageToShare(Bitmap bitmap) {
        File imageFolder=new File(MainActivity.this.getCacheDir(), "images");
        Uri uri=null;
        try {
            imageFolder.mkdirs();// create if not exist
            File file=new File(imageFolder, "shared_image.png");

            FileOutputStream stream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri= FileProvider.getUriForFile(MainActivity.this,"com.example.duoihinhbatchu.fileprovider",
                    file);

        }catch (Exception e){
            Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return uri;
    }

}