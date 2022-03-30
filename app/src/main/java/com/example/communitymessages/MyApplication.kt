package com.example.communitymessages

import android.app.Application
import com.example.communitymessages.di.AppComponent
import com.example.communitymessages.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}