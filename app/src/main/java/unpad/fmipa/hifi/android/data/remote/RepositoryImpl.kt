package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.mapper.Mapper
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.data.remote.model.Record
import unpad.fmipa.hifi.android.domain.Repository
import unpad.fmipa.hifi.android.domain.model.AirtableCalendarEvent

class RepositoryImpl(
    private val remoteSource: RemoteSource,
    private val calendarEventAirtableMapper: Mapper<List<Record>, List<AirtableCalendarEvent>>
    ) :
    Repository {

    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return remoteSource.getCalendarEvents()
    }

    override suspend fun getCalendarEventsAirtable(): List<AirtableCalendarEvent> {
        return calendarEventAirtableMapper.map(remoteSource.getCalendarEventsAirtable())
    }

}