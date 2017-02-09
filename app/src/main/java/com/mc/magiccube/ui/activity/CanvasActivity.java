package com.mc.magiccube.ui.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_canvas);
        setContentView(new View(this) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

            }

            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);

            }
        });
    }
}
