package com.mc.magiccube.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mc.library.base.BaseActivity;
import com.mc.magiccube.R;

/**
 * Created by dinghui on 2016/11/4.
 * 闪屏Activity
 */
/**
 * author：coder173025
 * project：MagicCube
 * package：com.mc.magiccube
 * email：coder173025@sina.com
 * data：2016/12/31
 */
public class SplashActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(this);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    protected void initWidget() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sample_text:
                startActivity(new Intent(this, CanvasActivity.class));
                break;

            default:
                break;
        }
    }
}
