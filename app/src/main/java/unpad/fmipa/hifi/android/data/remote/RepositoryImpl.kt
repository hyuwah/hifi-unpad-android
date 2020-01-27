package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse
import unpad.fmipa.hifi.android.domain.Repository

class RepositoryImpl(private val remoteSource: RemoteSource) :
    Repository {

    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return remoteSource.getCalendarEvents()
    }

}