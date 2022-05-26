package unpad.fmipa.hifi.android.data.remote.mapper

import unpad.fmipa.hifi.android.data.remote.model.Fields
import unpad.fmipa.hifi.android.data.remote.model.Record
import unpad.fmipa.hifi.android.domain.model.AirtableCalendarEvent

class CalendarEventAirtableMapper: Mapper<List<Record>, List<AirtableCalendarEvent>> {
    override fun map(input: List<Record>): List<AirtableCalendarEvent> {
        return input.map { it.toDomain() }
    }

    private fun Record.toDomain(): AirtableCalendarEvent {
        return AirtableCalendarEvent(id, fields.toDomain(), createdTime)
    }

    private fun Fields.toDomain(): AirtableCalendarEvent.Fields {
        return AirtableCalendarEvent.Fields(time.orEmpty(), name.orEmpty(), tags.orEmpty(), description.orEmpty(), cover.orEmpty())
    }
}