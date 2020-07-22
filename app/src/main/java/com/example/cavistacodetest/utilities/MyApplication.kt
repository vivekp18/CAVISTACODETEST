package com.example.cavistacodetest.utilities

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle

class MyApplication :Application(),Application.ActivityLifecycleCallbacks{

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)

    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppDetails.activity = activity;
        AppDetails.context = this

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {

    }


}