package com.shaen.myapplication.ui.market.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.shaen.myapplication.R;
import com.shaen.myapplication.common.adapter.SimpleFragmentPagerAdapter;
import com.shaen.myapplication.common.viewpager.NoTouchScrollViewpager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail_land);
        ButterKnife.bind(this);

        Fragment[] fragments = {ChartOneDayFragment.newInstance(true), ChartFiveDayFragment.newInstance(true),
                ChartKLineFragment.newInstance(1,true), ChartKLineFragment.newInstance(7,true),
                ChartKLineFragment.newInstance(30,true)};
        String[] titles = {"分时", "五日", "日K", "周K", "月K"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
