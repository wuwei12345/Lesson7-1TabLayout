package com.jash.lesson7_1;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SlidingPaneLayout.PanelSlideListener {

    private SlidingPaneLayout sliding;
    private LinearLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        sliding = (SlidingPaneLayout) findViewById(R.id.sliding);
        sliding.setSliderFadeColor(0);
        sliding.setPanelSlideListener(this);
        view = (LinearLayout) findViewById(R.id.container);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.format("第%02d页", i));
        }
        pager.setAdapter(new CustomAdapter(getSupportFragmentManager(), list));
        tab.setupWithViewPager(pager);
        pager.setOnTouchListener(new View.OnTouchListener() {
            private float x;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                }
                if (x > v.getWidth() / 7){
                    sliding.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        sliding.closePane();
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){

        }
        return true;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        ViewCompat.setPivotX(view, 0);
        ViewCompat.setScaleX(view, 1 - slideOffset * 0.5f);
        ViewCompat.setScaleY(view, 1 - slideOffset * 0.5f);
    }

    @Override
    public void onPanelOpened(View panel) {

    }

    @Override
    public void onPanelClosed(View panel) {

    }
}
