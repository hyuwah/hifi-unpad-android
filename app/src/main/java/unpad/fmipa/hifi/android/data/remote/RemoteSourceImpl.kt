package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.BuildConfig
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.data.remote.model.Record

class RemoteSourceImpl(private val calendarEventApi: CalendarEventApi) : RemoteSource {
    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return calendarEventApi.getEventList().body() ?: listOf()
    }

    override suspend fun getCalendarEventsAirtable(): List<Record> {
        return try {
            val response = calendarEventApi.getEventListAirtable(BuildConfig.AIRTABLE_KEY)
            if(response.isSuccessful) {
                response.body()?.records.orEmpty()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}