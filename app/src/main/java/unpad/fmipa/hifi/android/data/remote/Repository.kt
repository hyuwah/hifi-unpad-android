package unpad.fmipa.hifi.android.data.remote

import unpad.fmipa.hifi.android.data.IRepository
import unpad.fmipa.hifi.android.data.remote.model.CalendarEventResponse

class Repository(private val remoteSource: IRemoteSource): IRepository {

    override suspend fun getCalendarEvents(): List<CalendarEventResponse> {
        return remoteSource.getCalendarEvents()
    }

}