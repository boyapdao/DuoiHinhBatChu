package com.example.duoihinhbatchu.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.ChuDeActivity;
import com.example.duoihinhbatchu.DataBase;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.model.ChoiGameModel;
import com.example.duoihinhbatchu.object.ChuDe;
import com.example.duoihinhbatchu.object.NguoiDung;

import java.util.ArrayList;

public class Adapter_ChuDe extends BaseAdapter {
    String DATABASE_NAME="DuoiHinhBatChu.db";
    SQLiteDatabase database;
    NguoiDung nguoiDung;

    int Layout;
    ArrayList<ChuDe> ds;
    ChuDeActivity context;

    public Adapter_ChuDe(int layout, ArrayList<ChuDe> ds, ChuDeActivity context) {
        Layout = layout;
        this.ds = ds;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(Layout, null);

        ImageView img_cd=convertView.findViewById(R.id.img_dongCD);
        TextView tv_tencd=convertView.findViewById(R.id.tv_TenChuDe);
        TextView tv_Level=convertView.findViewById(R.id.tv_Level);
        ProgressBar progressBar=convertView.findViewById(R.id.progressbar_dongCD);

        RelativeLayout relativeLayout=convertView.findViewById(R.id.relative_dongcd);

        if(position%2.0==0) relativeLayout.setBackgroundResource(R.drawable.bg_dongcd);
        else relativeLayout.setBackgroundResource(R.drawable.bg_dongcd2);

        ChuDe cd=ds.get(position);

        tv_tencd.setText(cd.getTenCD());
        byte[] Hinh=cd.getHinh();

        Bitmap bitmap= BitmapFactory.decodeByteArray(Hinh,0, Hinh.length);
        img_cd.setImageBitmap(bitmap);

        //lấy ra số câu hỏi và số câu đã giải được
        int TongSoCau, SoCauDaGiai;
        database= DataBase.initDatabase(context, DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM CauHoi WHERE MaCD='"+cd.getMaCD()+"'", null);
        TongSoCau=cursor.getCount();

        nguoiDung=new NguoiDung();
        nguoiDung.maCD=cd.getMaCD();
        nguoiDung.getTT_Cau(context);
        SoCauDaGiai=nguoiDung.SoCau;

        tv_Level.setText(SoCauDaGiai+"/"+TongSoCau);
        progressBar.setMax(TongSoCau);
        progressBar.setProgress(SoCauDaGiai);

        return convertView;
    }
}
