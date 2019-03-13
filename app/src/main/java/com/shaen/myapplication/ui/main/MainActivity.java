package com.shaen.myapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.shaen.myapplication.R;
import com.shaen.myapplication.application.MyApplication;
import com.shaen.myapplication.ui.market.activity.StockDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("code", "cn_300228")
                                .add("start", "20190301")
                                .add("end", "20190312")
                                .add("stat", "1")
                                .add("order", "D")
                                .add("period", "d")
                                .add("callback", "historySearchHandler")
                                .add("rt", "json")
//                                .add("response", "json")
//                                .add("date", "20180120")
//                                .add("stockNo", "0050")
                                .build();

                        Request request = new Request.Builder()

//                                .url("http://www.twse.com.tw/exchangeReport/STOCK_DAY?")
                                .url("http://q.stock.sohu.com/hisHq?code=cn_601318&start=20190301&end=20190312&stat=1&order=D&period=d&callback=historySearchHandler&rt=json")
                                .post(formBody)
                                .build();

                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String string = response.body().string();
                                Log.d("aaaaaaaaaa", string);
                                Gson gson = new Gson();
                                try {
                                    JSONArray jsonArray = new JSONArray(string);
//                                    StockData stockData = gson.fromJson(jsonArray.get(0).toString(), StockData.class);
                                    Log.d("aaaaaaaaaa", jsonArray.get(0).toString());
                                    StockData1 stockData1 = gson.fromJson(jsonArray.get(0).toString(), StockData1.class);
                                    Collections.reverse(stockData1.hq);
                                    for (int i = 0; i < stockData1.hq.size(); i++) {
                                        Log.d("aaaaaaaaaa", stockData1.hq.get(i).get(0));
                                        for (int a = 0; a < stockData1.hq.get(i).size(); a++) {
                                            Log.d("aaaaaaaaaa", stockData1.hq.get(i).get(a));
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();


            }
        });



//        EasyHttp.post("/mktinfo_api/get_quot")
//                .upJson("{\"version\":\"1.4.2\",\"osType\":0,\"params\":{\"assetIds\":[\"00665.HK\",\"06837.HK\",\"HSI.IDX.HK\"],\"isRealQuote\":\"1\",\"fields\":\"0|1|2|9|10|38|36|42|15|999\",\"sessionId\":\"208a061421b141c591f8fc27dc2dd9537034\"},\"id\":\"1515401807153000024android1.4.2\",\"ranNum\":\"iBestAppfinalparams\",\"sign\":\"pIf56ZnwChlX5dpp0Kf3s\\/bPJlg=\"}")
//                .execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        Log.e("Error",e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(String response) {
//                        Log.e("Success",response);
//                    }
//                });

    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this, StockDetailActivity.class));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyApplication.getApplication().initDayNight();
    }
}
