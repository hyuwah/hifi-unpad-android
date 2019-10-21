package unpad.fmipa.hifi.android.data.remote

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface CalendarEventApi {

    @GET("https://api.sheety.co/5d26f414-3c71-48bb-a3b3-dd63b1b59a74")
    suspend fun getEventList(): Response<List<CalendarEventResponse>>

    companion object {
        fun create(): CalendarEventApi {
            val client = Retrofit.Builder()
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.sheety.co")
                .build()
            return client.create(CalendarEventApi::class.java)
        }
    }

}