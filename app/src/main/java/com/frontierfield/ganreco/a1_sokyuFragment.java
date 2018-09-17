package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class a1_sokyuFragment extends AppCompatActivity {

    // X軸最低スワイプ距離
    private static final int SWIPE_MIN_DISTANCE = 50;
    // X軸最低スワイプスピード
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1);
    }

    // タッチイベント
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("TouchEvent", "getAction()" + "ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("TouchEvent", "getAction()" + "ACTION_UP");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("TouchEvent", "getAction()" + "ACTION_MOVE");
                    startActivity(new Intent(getApplicationContext(), a2_sokyuFragment.class));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    Log.d("TouchEvent", "getAction()" + "ACTION_CANCEL");
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}