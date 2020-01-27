package unpad.fmipa.hifi.android.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import unpad.fmipa.hifi.android.app.di.apiModule
import unpad.fmipa.hifi.android.app.di.dataModule
import unpad.fmipa.hifi.android.app.di.viewModelModule

class HIFIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidLogger()
            androidContext(this@HIFIApplication)
            modules(
                listOf(
                    apiModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}