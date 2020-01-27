package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

class RemoteSourceImpl(private val calendarEventApi: CalendarEventApi) : RemoteSource {
    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return calendarEventApi.getEventList().body() ?: listOf()
    }
}