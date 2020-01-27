package unpad.fmipa.hifi.android.app.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import unpad.fmipa.hifi.android.data.remote.CalendarEventApi

val apiModule = module {

    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.sheety.co")
            .build()
    }

    single {
        get<Retrofit>().create(CalendarEventApi::class.java)
    }

}