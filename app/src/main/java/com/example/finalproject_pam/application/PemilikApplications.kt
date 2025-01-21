package com.example.finalproject_pam.application

import android.app.Application
import com.example.finalproject_pam.dependenciesinjection.AppContainer
import com.example.finalproject_pam.dependenciesinjection.PemilikContainer

class PemilikApplications: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = PemilikContainer()
    }
}