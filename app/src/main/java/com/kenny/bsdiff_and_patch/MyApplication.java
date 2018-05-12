package com.kenny.bsdiff_and_patch;

import android.app.Application;

import com.taobao.sophix.SophixManager;

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
