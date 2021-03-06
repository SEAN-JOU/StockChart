package com.shaen.myapplication.ui.market.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.stockChart.data.StockData;
import com.shaen.myapplication.R;
import com.shaen.myapplication.common.adapter.SimpleFragmentPagerAdapter;
import com.shaen.myapplication.common.data.Constants;
import com.shaen.myapplication.common.viewpager.NoTouchScrollViewpager;
import com.shaen.myapplication.ui.market.fragment.ChartFiveDayFragment;
import com.shaen.myapplication.ui.market.fragment.ChartKLineFragment;
import com.shaen.myapplication.ui.market.fragment.ChartOneDayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 股票详情页
 */
public class StockDetailActivity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    NoTouchScrollViewpager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    StockData stockData;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);

        try {
            bundle = getIntent().getExtras();
            StockData stockData = (StockData) bundle.getSerializable("data");


        }catch (Exception e){
            e.printStackTrace();
        }




        toolbar.setTitle("图表");
        toolbar.inflateMenu(R.menu.menu_right);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_model:
                        SharedPreferences sp = getSharedPreferences(Constants.SP_FILE,
                                Context.MODE_PRIVATE);
                        if(!sp.getBoolean(Constants.DAY_NIGHT_MODE,false)){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            sp.edit().putBoolean(Constants.DAY_NIGHT_MODE,true).apply();
                            Toast.makeText(StockDetailActivity.this, "夜间模式!", Toast.LENGTH_SHORT).show();
                        }else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            sp.edit().putBoolean(Constants.DAY_NIGHT_MODE,false).apply();
                            Toast.makeText(StockDetailActivity.this, "白天模式!", Toast.LENGTH_SHORT).show();
                        }
                        recreate();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        Fragment[] fragments = {ChartOneDayFragment.newInstance(false,stockData), ChartFiveDayFragment.newInstance(false,stockData),
                ChartKLineFragment.newInstance(1, false,stockData), ChartKLineFragment.newInstance(7, false,stockData),
                ChartKLineFragment.newInstance(30, false,stockData)};
        String[] titles = {"分时", "五日", "日K", "周K", "月K"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }




}
