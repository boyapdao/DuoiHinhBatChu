package com.example.duoihinhbatchu.model;

import com.example.duoihinhbatchu.ChoiGameActivity;
import com.example.duoihinhbatchu.DATA;
import com.example.duoihinhbatchu.object.CauDo;
import com.example.duoihinhbatchu.object.NguoiDung;
import java.util.ArrayList;

public class ChoiGameModel {

    ChoiGameActivity c;
    ArrayList<CauDo> arr;
    int CauSo=-1;
    NguoiDung nguoiDung;

    public ChoiGameModel(ChoiGameActivity c) {
        this.c = c;
        nguoiDung=new NguoiDung();
        taoData();
    }
    private void taoData(){
        arr=new ArrayList<>(DATA.getData().arrCauDo);

//        arr.add(new CauDo("Màn 1","baola","https://1.bp.blogspot.com/-AQYGAAPvMvE/U8jcVMz8ClI/AAAAAAAACyg/8LR7DOjs3bk/s1600/2014-07-17+23.37.47-1.png"));
//        arr.add(new CauDo("Màn 2","obama","https://lazi.vn/uploads/dhbc/1514479758_bc2.jpg"));
//        arr.add(new CauDo("Màn 3","yeuot","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcReFBIaFtgdlxbjZKL8T6vS9JUporAp0nRKEA&usqp=CAU"));
//        arr.add(new CauDo("Màn 4","yeutim","https://files.vforum.vn/2014/T10/img/vforum.vn-133676-10404432-755471441171774-684339299231733906-n.jpg"));
//        arr.add(new CauDo("Màn 5","kedon","https://vcdn-vnexpress.vnecdn.net/2015/06/01/3-4064-1433124775.jpg"));
    }
    public CauDo layCauDo(){
        CauSo++;
        if (CauSo>=arr.size()) CauSo=arr.size()-1;
        return arr.get(CauSo);
    }

    public void layThongTin(){
        nguoiDung.getTT(c);
    }
    public void luuThongTin(){
        nguoiDung.saveTT(c);
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
