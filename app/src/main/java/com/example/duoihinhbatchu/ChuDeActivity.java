package com.example.duoihinhbatchu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duoihinhbatchu.adapter.Adapter_ChuDe;
import com.example.duoihinhbatchu.object.ChuDe;

import java.util.ArrayList;

public class ChuDeActivity extends AppCompatActivity {

    String DATABASE_NAME="DuoiHinhBatChu.db";
    SQLiteDatabase database;

    ListView lv;
    ArrayList<ChuDe> ds_ChuDe=new ArrayList<>();
    Adapter_ChuDe adapter;

    ImageView img_back;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chude);

//        Context context=ChuDeActivity.this;
//        context.deleteDatabase(DATABASE_NAME);

        Init();
        ReadData();
        Events();
    }

    private void Events() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int SoCauHoi;
                database=DataBase.initDatabase(ChuDeActivity.this, DATABASE_NAME);
                Cursor cursor=database.rawQuery("SELECT * FROM CauHoi WHERE MaCD='"+ds_ChuDe.get(position).getMaCD()+"'", null);
                SoCauHoi=cursor.getCount();
                if(SoCauHoi<=0){
                    Toast.makeText(ChuDeActivity.this, "Chức năng này đang phát triển!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent it = new Intent(ChuDeActivity.this, ChoiGameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("MaCD", ds_ChuDe.get(position).getMaCD());
                    it.putExtras(bundle);
                    startActivity(it);
                    finish();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ReadData() {
        database= DataBase.initDatabase(ChuDeActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM ChuDe",null);
        ds_ChuDe.clear();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int ma=cursor.getInt(0);
            String ten=cursor.getString(1);
            byte[] Hinh=cursor.getBlob(2);

            ds_ChuDe.add(new ChuDe(ma,ten,Hinh));
        }
        adapter.notifyDataSetChanged();
    }

    private void Init() {

        lv=findViewById(R.id.lv_ChuDe);
        adapter=new Adapter_ChuDe(R.layout.item_chude,ds_ChuDe,ChuDeActivity.this);
        lv.setAdapter(adapter);

        img_back=findViewById(R.id.img_CDBack);
    }
}
