package com.ylx.zxingtestdemo.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ylx.zxingtestdemo.R;

public class BasicActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("=====ActivityName=====:","" + this.getClass().getName());
    }
}
