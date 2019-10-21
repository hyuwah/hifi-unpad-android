package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

class RemoteSource(private val calendarEventApi: CalendarEventApi) : IRemoteSource {
    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return calendarEventApi.getEventList().body() ?: listOf()
    }
}