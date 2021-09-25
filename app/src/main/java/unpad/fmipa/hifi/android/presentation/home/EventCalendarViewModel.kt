package unpad.fmipa.hifi.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import unpad.fmipa.hifi.android.domain.Repository
import unpad.fmipa.hifi.android.presentation.model.CalendarEvent
import java.time.LocalDate
import java.util.*

class EventCalendarViewModel(private val repository: Repository) : ViewModel() {

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