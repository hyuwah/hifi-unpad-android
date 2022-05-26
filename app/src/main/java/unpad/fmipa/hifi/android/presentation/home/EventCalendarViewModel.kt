package unpad.fmipa.hifi.android.presentation.home

import android.util.Log
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
                val airtableResult = repository.getCalendarEventsAirtable()
                //val result = repository.getCalendarEvents()
                val holder = mutableMapOf<LocalDate, List<CalendarEvent>>()
                airtableResult.forEach { event ->
                    val localDate = event.fields.localDateTime?.toLocalDate()
                    localDate?.let {
                        val calendarEvent = CalendarEvent(
                            UUID.randomUUID().toString(),
                            event.fields.name,
                            localDate
                        )
                        val temp = holder[localDate] as MutableList? ?: mutableListOf()
                        temp.add(calendarEvent)
                        holder[localDate] = temp
                    }
                }
                _events.postValue(holder)
            } catch (e: Throwable) {
                Log.e("Airtable", e.message.orEmpty())
            }
        }
    }
}