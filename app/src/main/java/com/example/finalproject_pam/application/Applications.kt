package com.example.finalproject_pam.application

import android.app.Application
import com.example.session12.dependenciesinjection.AppContainer
import com.example.session12.dependenciesinjection.Container

class Applications: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}