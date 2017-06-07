package com.bessttcom.xmenw.kotlinz

import android.app.Application
import com.squareup.otto.Bus


class App : Application() {


    companion object {
        @JvmField
        var bus: Bus? = null
    }

    override fun onCreate() {
        super.onCreate()
        bus = Bus()
    }


}