package com.app.skreppis.skreppis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import butterknife.*;

public class SearchDriverCriteriaActivity extends BaseActivity {

    @BindView(R.id.driverList)
    RecyclerView driverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_driver_criteria);

        ButterKnife.bind(this);
        //set layout manager for list view

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        driverList.setLayoutManager(staggeredGridLayoutManager);

        //create recyclerview adapter




    }
}
