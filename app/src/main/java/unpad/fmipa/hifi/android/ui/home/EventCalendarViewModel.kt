package unpad.fmipa.hifi.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import unpad.fmipa.hifi.android.data.remote.CalendarEventApi
import unpad.fmipa.hifi.android.data.remote.RemoteSource
import unpad.fmipa.hifi.android.data.remote.Repository
import unpad.fmipa.hifi.android.ui.model.CalendarEvent
import java.util.*

class EventCalendarViewModel : ViewModel() {
    private val repository = Repository(RemoteSource(CalendarEventApi.create()))

    private val _events = MutableLiveData<MutableMap<LocalDate, List<CalendarEvent>>>()
    val events = _events as LiveData<MutableMap<LocalDate, List<CalendarEvent>>>

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getCalendarEvents()
                val holder = mutableMapOf<LocalDate, List<CalendarEvent>>()
                result.forEach {
                    val localDate = LocalDate.of(it.year, it.month, it.date)
                    val calendarEvent = CalendarEvent(
                        UUID.randomUUID().toString(),
                        it.event,
                        localDate
                    )
                    val temp = holder[localDate] as MutableList? ?: mutableListOf()
                    temp.add(calendarEvent)
                    holder[localDate] = temp
                }
                _events.postValue(holder)
            } catch (e: Throwable) {

            }
        }
    }
}