package unpad.fmipa.hifi.android.data

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

interface IRepository {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>
}