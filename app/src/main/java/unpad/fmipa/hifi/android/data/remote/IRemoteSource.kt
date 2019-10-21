package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface IRemoteSource {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>
}