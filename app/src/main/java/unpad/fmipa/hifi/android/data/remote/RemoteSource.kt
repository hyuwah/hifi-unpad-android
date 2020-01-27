package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface RemoteSource {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>
}