package com.shaen.myapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.stockChart.data.StockData;
import com.google.gson.Gson;
import com.shaen.myapplication.R;
import com.shaen.myapplication.application.MyApplication;
import com.shaen.myapplication.ui.market.activity.StockDetailActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.shaen.myapplication.application.MyApplication.API_KEY;
import static com.shaen.myapplication.application.MyApplication.API_URL_STOCK;


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
                                .add("appid", API_KEY)
                                .add("code", "000001")//(必填)證券代碼
                                .add("index", "true")//(必填)code是否為指數
                                .add("k_type", "day")//(必填)day=日線,week=周線,month=月線,5=五分,15=十五分,30=三十分,60=六十分
                                .add("fq_type", "qfq")//(必填)除權前qfq 除權後hfq
                                .add("start_date", "2019-03-01")//(非必填)開始日期YYYY-MM-DD
                                .add("end_date", "2019-03-14")//(非必填)結束日期YYYY-MM-DD
                                .build();

                        Request request = new Request.Builder()
                                .url(API_URL_STOCK)
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
                                StockData stockData = gson.fromJson(string, StockData.class);
//                                    Collections.reverse(stockData.data);
//                                    for (int i = 0; i < stockData.data.size(); i++) {
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).close);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).date);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).open);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).low);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).high);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).volume);
//                                        Log.d("aaaaaaaaaa", stockData.data.get(i).code);
//                                    }
                                    Bundle bundle =new Bundle();
                                    bundle.putSerializable("data",stockData);
                                    Intent intent = new Intent(MainActivity.this,StockDetailActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);



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
