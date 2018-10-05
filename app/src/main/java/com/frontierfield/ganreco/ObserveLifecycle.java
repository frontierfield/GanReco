package com.frontierfield.ganreco;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

public class ObserveLifecycle implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void handleCreate(){
        Log.d("ObserveLifecycle", "ON_CREATE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void handleDestroy(){
        Log.d("ObserveLifecycle", "ON_DESTROY");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void handlePause(){
        Log.d("ObserveLifecycle", "ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void handleResume(){
        Log.d("ObserveLifecycle", "ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void handleStart(){
        Log.d("ObserveLifecycle", "ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void handleStop(){
        Log.d("ObserveLifecycle", "ON_STOP");
    }
}