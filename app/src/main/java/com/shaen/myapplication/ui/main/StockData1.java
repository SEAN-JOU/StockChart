package com.shaen.myapplication.ui.main;

import java.io.Serializable;
import java.util.ArrayList;


public class StockData1 implements Serializable {

    public String error_code;
    public ArrayList<StockDataContent> data;
    public String reason;

    public class StockDataContent implements Serializable {

        public String date;
        public String open;
        public String close;
        public String high;
        public String low;
        public String volume;
        public String code;


    }

}




