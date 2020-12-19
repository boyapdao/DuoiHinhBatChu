package com.example.duoihinhbatchu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.adapter.DapAnAdapter;
import com.example.duoihinhbatchu.model.ChoiGameModel;
import com.example.duoihinhbatchu.object.CauDo;
import com.example.duoihinhbatchu.object.NguoiDung;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ChoiGameActivity extends AppCompatActivity {

    ChoiGameModel model;
    CauDo cauDo;
    int MaCD;
    String DapAn = "";
    int Index = 0;
    ArrayList<String> arrCauTraLoi;
    TextView[] tvCTL;

    LinearLayout linear1, linear2;

    ArrayList<String> arrDapAn;
    GridView gdvDapAn;
    ImageView imgAnhCauHoi;
    TextView tv_TienNguoiDung, tv_Cau;

    ImageView imgGoiY, img_back, img_share;
    boolean trangThaiGoiy = false;

    Animation anim_rotate, anim_rotate_dapansai, anim_rotate_dapandung;

    private static int WRITE_EXTERNAL_REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);
        Intent it=getIntent();
        if (it != null)
        {
            Bundle bundle=it.getExtras();
            if(bundle!=null)
                MaCD= bundle.getInt("MaCD");
        }
        Init();
        Events();
        hienCauDo();
    }

    private void Events() {

        //sự kiện chơi game click đáp án
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

        imgGoiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ChoiGameActivity.this);
                alert.setTitle("Mở ô đáp án");
                alert.setMessage("Dùng 10 Ruby để mở một ô chữ. Bạn có chắc chắn không?");
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Trừ tiền người dùng

                        if (model.getNguoiDung().tien < 10) {
                            Toast.makeText(ChoiGameActivity.this, "Không đủ ruby", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        model.getNguoiDung().tien = model.getNguoiDung().tien - 10;
                        model.luuThongTin();
                        tv_TienNguoiDung.setText(model.getNguoiDung().tien + "");

                        trangThaiGoiy = true;

                        //set Animation
                        for (int i = 0; i < DapAn.length(); i++) {
                            tvCTL[i].startAnimation(anim_rotate);
                        }
                    }
                });
                alert.show();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(ChoiGameActivity.this);
                alert.setTitle("Thoát!!!");
                alert.setMessage("Bạn có muốn thoát không?");
                alert.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ChoiGameActivity.this, ChuDeActivity.class));
                        finish();
                    }
                });
                alert.setNegativeButton("Chơi tiếp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Check permission
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChoiGameActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, WRITE_EXTERNAL_REQUEST_CODE);
                }
                else {
                    LinearLayout linearLayout=findViewById(R.id.linear_ChoiGame);
                    Bitmap bitmap = getScreenshot(linearLayout.getRootView());

                    Uri uri=saveImageToShare(bitmap);
                    Toast.makeText(ChoiGameActivity.this, "Đã chụp màn hình để chia sẻ", Toast.LENGTH_SHORT).show();

                    //share intent
                    Intent sIntent=new Intent(Intent.ACTION_SEND);
                    sIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    sIntent.setType("image/png");
                    startActivity(Intent.createChooser(sIntent, "Share Via"));
                }
            }
        });
    }

    private void Init() {
//        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauHoi = findViewById(R.id.img_anhCauDo);
        tv_TienNguoiDung = findViewById(R.id.tv_tienNguoiDung);
        tv_Cau=findViewById(R.id.tv_Cau);

        imgGoiY = findViewById(R.id.img_goiy);
        img_back = findViewById(R.id.img_back);
        img_share=findViewById(R.id.img_shareGame);

        model = new ChoiGameModel(ChoiGameActivity.this, MaCD);
        arrCauTraLoi = new ArrayList<>();
        arrDapAn = new ArrayList<>();

//        Hiện câu trả lời bằng view dinamically
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);

        //animation
        anim_rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        anim_rotate_dapansai = AnimationUtils.loadAnimation(this, R.anim.rotate_dapansai);
        anim_rotate_dapandung=AnimationUtils.loadAnimation(this, R.anim.rotate_dapandung);
    }

    private void hienCauDo() {
        cauDo = model.layCauDo();
        DapAn = cauDo.getDapAn();

        if(String.valueOf(cauDo.getLevel()).length()==1)
            tv_Cau.setText("0"+cauDo.getLevel());
        else tv_Cau.setText(cauDo.getLevel()+"");
        bamData();
        hienDapAn();

        byte[] Hinh=cauDo.getAnh_2();

        Bitmap bm_HinhCD= BitmapFactory.decodeByteArray(Hinh, 0, Hinh.length);
        imgAnhCauHoi.setImageBitmap(bm_HinhCD);
//        Glide.with(this)
//                .load(cauDo.getAnh())
//                .into(imgAnhCauHoi);
        model.layThongTin();
        tv_TienNguoiDung.setText(model.getNguoiDung().tien + "");
    }

    private void hienCauTraLoi() {
        //đẩy câu trả lời lên màn hình
        //Load lại câu trả lời

        //khi sang linear2 vị trí bắt đầu lại = 0
        int j=-1;
        for (int i = 0; i < arrCauTraLoi.size(); i++) {
            String ctl = arrCauTraLoi.get(i);
            if (i < 7) {
                View v = linear1.getChildAt(i);
                tvCTL[v.getId()].setText(ctl);
            } else {
                j++;
                View v = linear2.getChildAt(j);// nên ko thể dùng biến i
                tvCTL[v.getId()].setText(ctl);
            }
        }
    }

    private void hienDapAn() {//đẩy đáp án lên màn hình
        if (arrDapAn.size() > 8) gdvDapAn.setNumColumns(8);
        else gdvDapAn.setNumColumns(arrDapAn.size());

        gdvDapAn.setAdapter(new DapAnAdapter(this, 0, arrDapAn));
    }

    private void checkWin() {
        String s = "";
        for (String s1 : arrCauTraLoi) {
            s = s + s1;
        }
        s = s.toUpperCase();
        if (s.equals(DapAn.toUpperCase())) {
            Toast.makeText(this, "Bạn đã chiến thắng", Toast.LENGTH_LONG).show();

            if(MainActivity.mo_Nhac) {
                MediaPlayer mediaPlayer = MediaPlayer.create(ChoiGameActivity.this, R.raw.nhac_tre_con);
                mediaPlayer.start();
            }
            //load animation
            for (int i = 0; i < DapAn.length(); i++) {
                tvCTL[i].startAnimation(anim_rotate_dapandung);
            }
            new CountDownTimer(1500, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    // lưu trạng thái chơi

                    model.layThongTin();
                    model.getNguoiDung().tien = model.getNguoiDung().tien + 10;
                    model.getNguoiDung().maCD=MaCD;
                    model.getNguoiDung().SoCau=cauDo.getLevel();
                    model.luuThongTin();

                    Intent it = new Intent(ChoiGameActivity.this, ThongBaoActivity.class);

                    String DapAnCoDau=cauDo.getDapAnCoDau();
                    Bundle bundle=new Bundle();
                    bundle.putString("DapAnCoDau",DapAnCoDau);
                    it.putExtras(bundle);
                    startActivity(it);

                    new CountDownTimer(500, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            for(int i=0;i<DapAn.length();i++){
                                tvCTL[i].clearAnimation();
                            }
                            hienCauDo();
                        }
                    }.start();
                }
            }.start();

        } else {
            if (s.length() == DapAn.length()) {

                if(MainActivity.mo_Nhac){
                    MediaPlayer mediaPlayer=MediaPlayer.create(ChoiGameActivity.this, R.raw.nhac_that_bai);
                    mediaPlayer.start();
                }

                for (int i = 0; i < DapAn.length(); i++) {
                    if (tvCTL[i].isEnabled())
                        tvCTL[i].setTextColor(Color.RED);
                    tvCTL[i].startAnimation(anim_rotate_dapansai);
                }
                new CountDownTimer(2000, 10) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        for (int i = 0; i < DapAn.length(); i++) {
                            tvCTL[i].clearAnimation();
                            if(tvCTL[i].isEnabled())
                                tvCTL[i].setTextColor(Color.BLACK);
                        }
                    }
                }.start();
            }
        }
    }

    private void bamData() {
        // bamData đáp án
        Index = 0;
        arrDapAn.clear();
        arrCauTraLoi.clear();

        Random r = new Random();
        for (int i = 0; i < DapAn.length(); i++) {
            //thêm chữ cái đúng vào đáp án để lựa chọn
            String s1 = DapAn.charAt(i) + "";
            arrDapAn.add(s1);
        }
        for (int i = 0; i < (16 - DapAn.length()); i++) {
            //Thêm ký tự random vào số còn lại
            String s = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s);
        }
        //Set arrDapAn all viết hoa
        for (int i = 0; i < DapAn.length(); i++) {
            String s = "" + DapAn.charAt(i);
            arrDapAn.set(i, s.toUpperCase());
        }
        Collections.shuffle(arrDapAn);// trộn đáp án

        //BamData Câu trả lời-----------------------------------------------------
        linear1.removeAllViews();
        linear2.removeAllViews();

        tvCTL = new TextView[DapAn.length()]; //khởi tạo viewChild
        for (int i = 0; i < DapAn.length(); i++) {
            arrCauTraLoi.add("");

            tvCTL[i] = new TextView(ChoiGameActivity.this);
            tvCTL[i].setId(i);
            tvCTL[i].setBackgroundResource(R.drawable.bg_cautraloi);
            tvCTL[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvCTL[i].setGravity(Gravity.CENTER);
            tvCTL[i].setTextColor(Color.BLACK);
            tvCTL[i].setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(110, 110);
            layoutParams.setMargins(10, 10, 10, 20);// android:layout_margin="" đơn vị pixel
            tvCTL[i].setLayoutParams(layoutParams);
            if (i < 7) linear1.addView(tvCTL[i]);
            else linear2.addView(tvCTL[i]);

            final int finalI = i;
            final int finalI1 = i;
            tvCTL[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Sự kiện chơi game click view Câu trả lời

                    String s = tvCTL[finalI].getText().toString();
                    if (s.length() != 0 && trangThaiGoiy == false) {
                        Index = finalI;
                        arrCauTraLoi.set(finalI, "");
                        for (int i = 0; i < arrDapAn.size(); i++) {
                            if (arrDapAn.get(i).length() == 0) {
                                arrDapAn.set(i, s);
                                break;
                            }
                        }
                        hienCauTraLoi();
                        hienDapAn();
                    }
                    if (trangThaiGoiy) {
                        moGoiY(finalI1);
                        trangThaiGoiy = false;

                        checkWin();
                    }
                }
            });
        }
    }

    public void moGoiY(int id) {

        int position;
        position = id;
        String s = DapAn.charAt(position) + "";
        s = s.toUpperCase();

        arrCauTraLoi.set(position, s);
        tvCTL[position].setText(s);
        tvCTL[position].setEnabled(false);
        tvCTL[position].setTextColor(Color.GREEN);

        for (int i = 0; i < arrDapAn.size(); i++) {
            if (arrDapAn.get(i).toUpperCase().equals(s)) {
                arrDapAn.set(i, "");
                hienDapAn();
                break;
            }
        }

        //stop animation
        for (int i = 0; i < DapAn.length(); i++) {
            tvCTL[i].clearAnimation();
        }
    }

    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    private Uri saveImageToShare(Bitmap bitmap) {
        File imageFolder=new File(ChoiGameActivity.this.getCacheDir(), "images");
        Uri uri=null;
        try {
            imageFolder.mkdirs();// create if not exist
            File file=new File(imageFolder, "shared_image.png");

            FileOutputStream stream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri= FileProvider.getUriForFile(ChoiGameActivity.this,"com.example.duoihinhbatchu.fileprovider",
                    file);

        }catch (Exception e){
            Toast.makeText(ChoiGameActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return uri;
    }
}
