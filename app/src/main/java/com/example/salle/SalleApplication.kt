package com.example.salle

import android.app.Application
import com.example.salle.data.AppContainer
import com.example.salle.data.AppDataContainer

class SalleApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}