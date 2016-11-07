package com.mc.magiccube.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mc.library.base.BaseActivity;
import com.mc.library.database.realm.DBHelper;
import com.mc.library.database.realm.Person;
import com.mc.magiccube.R;

/**
 * Created by dinghui on 2016/11/4.
 * 闪屏Activity
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
        Person person = new Person();
        person.setId(1);
        person.setName("hui");
        DBHelper.createOrUpdate(person);
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

    }
}
