package com.example.duoihinhbatchu;

import com.example.duoihinhbatchu.object.CauDo;

import java.util.ArrayList;

public class DATA {
    private static DATA data;
    static {
        data=new DATA();
    }
    public static DATA getData(){
        return data;
    }
    public ArrayList<CauDo> arrCauDo=new ArrayList<>();

}
