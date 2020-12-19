package com.example.duoihinhbatchu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duoihinhbatchu.R;

import java.util.ArrayList;
import java.util.List;

public class CauTraLoiAdapter extends ArrayAdapter<String> {

    private Context myCt;
    private ArrayList<String> arr;
    public CauTraLoiAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.myCt=context;
        this.arr=new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) myCt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_cau_tra_loi,null);
        }
        TextView txvCauTraLoi=convertView.findViewById(R.id.txvCauTraLoi);
//        if(this.arr.get(position).equals("")){
//            txvCauTraLoi.setBackgroundResource(R.drawable.bg_item_cautraloi);
//        }
//        else {
//            txvCauTraLoi.setBackgroundColor(Color.parseColor("#009688"));
//        }
        txvCauTraLoi.setText(this.arr.get(position));
        return convertView;
    }
}

