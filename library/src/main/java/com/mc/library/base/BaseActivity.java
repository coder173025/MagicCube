package com.mc.library.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dinghui on 2016/11/4.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected abstract void initWidget();
}
