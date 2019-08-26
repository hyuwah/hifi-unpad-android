package unpad.fmipa.hifi.android.ui.base.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day.view.*
import org.threeten.bp.LocalDate

class DayViewContainer(view: View, onSelectDate: (date:LocalDate) -> Unit) : ViewContainer(view) {
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val textView = view.exThreeDayText
    val dotView = view.exThreeDotView
    val todayBg = view.iv_today_bg

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                onSelectDate(day.date)
            }
        }
    }
}