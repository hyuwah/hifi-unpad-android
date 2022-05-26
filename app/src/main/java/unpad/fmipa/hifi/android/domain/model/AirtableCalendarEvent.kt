package unpad.fmipa.hifi.android.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AirtableCalendarEvent(
    val id: String,
    val fields: Fields,
    val createdTime: String
) {
    data class Fields(
        val time: String,
        val name: String,
        val tags: List<String>,
        val description: String,
        val coverImg: String
    ) {
        val localDateTime: LocalDateTime?
            get() = try {
                LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
            } catch (e: Exception) {
                null
            }
    }
}