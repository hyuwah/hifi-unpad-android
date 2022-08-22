package unpad.fmipa.hifi.android.helpers

import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.temporal.WeekFields
import java.util.*

object TimeHelper {
    private val mmmmFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val mmmYYYYFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val dMmmYYYYFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    fun formatMmmYyyy(temporalAccessor: TemporalAccessor): String =
        try {
            mmmYYYYFormatter.format(temporalAccessor)
        } catch (e: Exception) {
            ""
        }
    fun formatdMmmYyyy(temporalAccessor: TemporalAccessor): String =
        try {
            dMmmYYYYFormatter.format(temporalAccessor)
        } catch (e: Exception) {
            ""
        }

    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

}