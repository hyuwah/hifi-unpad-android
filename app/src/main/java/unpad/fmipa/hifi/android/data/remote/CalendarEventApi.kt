package unpad.fmipa.hifi.android.data.remote

import retrofit2.Response
import retrofit2.http.GET
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface CalendarEventApi {

    @GET("https://api.sheety.co/5d26f414-3c71-48bb-a3b3-dd63b1b59a74")
    suspend fun getEventList(): Response<List<CalendarEventResponse>>

}