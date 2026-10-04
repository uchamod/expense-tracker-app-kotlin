package com.example.pocketledger

import android.app.Application
import com.example.pocketledger.di.AppContainer

class PocketLedgerApplication : Application(){

    val container : AppContainer by lazy{
        AppContainer(this)
    }
}