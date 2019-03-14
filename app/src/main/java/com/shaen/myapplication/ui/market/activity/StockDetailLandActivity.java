package com.shaen.myapplication.ui.market.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.stockChart.data.StockData;
import com.shaen.myapplication.R;
import com.shaen.myapplication.common.adapter.SimpleFragmentPagerAdapter;
import com.shaen.myapplication.common.viewpager.NoTouchScrollViewpager;
import com.shaen.myapplication.ui.main.StockData1;
import com.shaen.myapplication.ui.market.fragment.ChartFiveDayFragment;
import com.shaen.myapplication.ui.market.fragment.ChartKLineFragment;
import com.shaen.myapplication.ui.market.fragment.ChartOneDayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 股票详情页-横屏
 */
public class StockDetailLandActivity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    NoTouchScrollViewpager viewPager;
    StockData stockData;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail_land);
        ButterKnife.bind(this);

        try {
            bundle = getIntent().getExtras();
            stockData = (StockData) bundle.getSerializable("data");
            for (int i = 0; i < 220//stockData.data.size()
                    ; i++) {

                synchronized(StockDetailLandActivity.this) {
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).close);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).date);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).open);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).low);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).high);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).volume);
                    Log.d("aaaaaaaaaa"+i, stockData.data.get(i).code);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }




        Fragment[] fragments = {ChartOneDayFragment.newInstance(true,stockData), ChartFiveDayFragment.newInstance(true,stockData),
                ChartKLineFragment.newInstance(1,true,stockData), ChartKLineFragment.newInstance(7,true,stockData),
                ChartKLineFragment.newInstance(30,true,stockData)};
        String[] titles = {"分时", "五日", "日K", "周K", "月K"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
