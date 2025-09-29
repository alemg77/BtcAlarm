package com.alarm

import android.app.Application
import android.util.Log

class App: Application()  {

    override fun onCreate() {
        super.onCreate()
        Log.v("TAGGG", "App: onCreate")
    }
}