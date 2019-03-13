package com.shaen.myapplication.ui.main;

import java.io.Serializable;
import java.util.ArrayList;




public class StockData implements Serializable {

    String stat;
    String date;
    String title;
    ArrayList<String> fields;
    ArrayList<ArrayList<String>> data;
    ArrayList<String> notes;


}


