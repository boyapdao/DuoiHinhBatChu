package com.example.duoihinhbatchu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.object.NguoiChoi;

import java.util.ArrayList;

public class NguoiChoiAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<NguoiChoi> ds;

    public NguoiChoiAdapter(Context context, int layout, ArrayList<NguoiChoi> ds) {
        this.context = context;
        this.layout = layout;
        this.ds = ds;
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
    class ViewHolder{
        ImageView imgRank, imgNguoiChoi;
        TextView tvRank, tvTen, tvThanhTich;
        RelativeLayout relativeLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout, null);
            holder=new ViewHolder();
            holder.imgRank=convertView.findViewById(R.id.img_Rank);
            holder.imgNguoiChoi=convertView.findViewById(R.id.img_NguoiChoi);
            holder.tvRank=convertView.findViewById(R.id.tv_Rank);
            holder.tvTen=convertView.findViewById(R.id.tv_TenNC);
            holder.tvThanhTich=convertView.findViewById(R.id.tv_ThanhTichNC);
            holder.relativeLayout=convertView.findViewById(R.id.relative_itemRank);
            convertView.setTag(holder);
        }else{holder= (ViewHolder) convertView.getTag();}

        NguoiChoi nc=ds.get(position);

        if (position%2==0) holder.relativeLayout.setBackgroundColor(Color.parseColor("#f2f0f1"));
        else  holder.relativeLayout.setBackgroundColor(Color.parseColor("#fafafa"));

        if (position<3){

            holder.imgRank.setVisibility(View.VISIBLE);
            holder.tvRank.setVisibility(View.INVISIBLE);

            switch (position){
                case 0:
                    holder.imgRank.setImageResource(R.drawable.rank1);
                    holder.tvTen.setTextColor(Color.parseColor("#e91d1d"));
                    holder.tvThanhTich.setTextColor(Color.parseColor("#e91d1d"));
                    LoadTT(holder, position, nc);
                    break;
                case 1:
                    holder.imgRank.setImageResource(R.drawable.rank2);
                    holder.tvTen.setTextColor(Color.parseColor("#fcab5f"));
                    holder.tvThanhTich.setTextColor(Color.parseColor("#fcab5f"));
                    LoadTT(holder, position, nc);
                    break;
                case 2:
                    holder.imgRank.setImageResource(R.drawable.rank3);
                    holder.tvTen.setTextColor(Color.parseColor("#e3cc70"));
                    holder.tvThanhTich.setTextColor(Color.parseColor("#e3cc70"));
                    LoadTT(holder, position, nc);
                    break;
            }
        }else {
            holder.imgRank.setVisibility(View.INVISIBLE);
            holder.tvRank.setVisibility(View.VISIBLE);

            LoadTT(holder, position, nc);
        }
        return convertView;
    }

    private void LoadTT(ViewHolder holder, int position, NguoiChoi nc) {
        holder.tvRank.setText((position+1)+"");
        holder.tvTen.setText(nc.ten);
        holder.tvThanhTich.setText(nc.thanhtich);
        Glide.with(context).load(nc.image).into(holder.imgNguoiChoi);
    }
}
