package unpad.fmipa.hifi.android.ui.base.calendar

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout

class MonthCardView(context: Context) : FrameLayout(context) {

    init {
        //setBackgroundResource(R.drawable.calendar_today_bg)
        setBackgroundColor(Color.parseColor("#ffffff"))
    }
}