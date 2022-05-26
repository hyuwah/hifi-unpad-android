package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.data.remote.model.Record

interface RemoteSource {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>

    suspend fun getCalendarEventsAirtable(): List<Record>
}