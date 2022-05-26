package unpad.fmipa.hifi.android.domain

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.data.remote.model.Record
import unpad.fmipa.hifi.android.domain.model.AirtableCalendarEvent

interface Repository {

    suspend fun getCalendarEvents(): List<CalendarEventResponse>

    suspend fun getCalendarEventsAirtable(): List<AirtableCalendarEvent>
}