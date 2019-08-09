package unpad.fmipa.hifi.android

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class HIFIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}