package unpad.fmipa.hifi.android.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventsAirtableResponse
import unpad.fmipa.hifi.android.data.remote.model.Record

interface CalendarEventApi {

    @GET("https://api.sheety.co/5d26f414-3c71-48bb-a3b3-dd63b1b59a74")
    suspend fun getEventList(): Response<List<CalendarEventResponse>>

    @GET("https://api.airtable.com/v0/appn3iet06EwWUneI/Table%201?maxRecords=3&view=Grid%20view")
    suspend fun getEventListAirtable(@Query("api_key") apiKey: String): Response<CalendarEventsAirtableResponse>

}