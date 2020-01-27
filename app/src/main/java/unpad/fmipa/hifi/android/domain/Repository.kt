package unpad.fmipa.hifi.android.domain

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface Repository {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>
}