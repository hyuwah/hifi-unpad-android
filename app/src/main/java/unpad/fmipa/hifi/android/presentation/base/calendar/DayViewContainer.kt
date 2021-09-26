package unpad.fmipa.hifi.android.presentation.base.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import unpad.fmipa.hifi.android.R
import java.time.LocalDate

class DayViewContainer(view: View, onSelectDate: (date: LocalDate) -> Unit) : ViewContainer(view) {
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val textView = view.findViewById<TextView>(R.id.exThreeDayText)
    val dotView = view.findViewById<View>(R.id.exThreeDotView)
    val todayBg = view.findViewById<ImageView>(R.id.iv_today_bg)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                onSelectDate(day.date)
            }
        }
    }
}