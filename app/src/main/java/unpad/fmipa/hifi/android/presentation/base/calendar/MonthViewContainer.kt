package unpad.fmipa.hifi.android.presentation.base.calendar

import android.view.View
import android.widget.LinearLayout
import com.kizitonwose.calendarview.ui.ViewContainer
import unpad.fmipa.hifi.android.R

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = view.findViewById<LinearLayout>(R.id.legendLayout)
}