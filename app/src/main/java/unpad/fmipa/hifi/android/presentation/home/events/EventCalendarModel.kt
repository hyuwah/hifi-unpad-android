package unpad.fmipa.hifi.android.presentation.home.events

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewParent
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.FragmentCalendarEventBinding
import unpad.fmipa.hifi.android.domain.model.CalendarEvent
import unpad.fmipa.hifi.android.helpers.TimeHelper
import unpad.fmipa.hifi.android.helpers.TimeHelper.daysOfWeekFromLocale
import unpad.fmipa.hifi.android.helpers.getColorCompat
import unpad.fmipa.hifi.android.presentation.base.calendar.DayViewContainer
import unpad.fmipa.hifi.android.presentation.base.calendar.MonthViewContainer
import java.time.LocalDate
import java.time.YearMonth

@EpoxyModelClass(layout = R.layout.fragment_calendar_event)
abstract class EventCalendarModel: EpoxyModelWithHolder<EventCalendarModel.Holder>() {

    companion object {
        const val ID = "EventCalendarModel"
    }

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    @EpoxyAttribute
    open var events = mutableMapOf<LocalDate, List<CalendarEvent>>()

    private val eventsAdapter = CalendarEventsAdapter {
        //Toast.makeText(requireContext(), "Click ${it.date}", Toast.LENGTH_SHORT).show()
    }

    override fun getDefaultLayout() = R.layout.fragment_calendar_event

    override fun createNewHolder(parent: ViewParent) = Holder()

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.binding) {
            setupCalendar()
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
    }

    private fun FragmentCalendarEventBinding.setupCalendar() {
        rvCalendarEvent.layoutManager =
            LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        rvCalendarEvent.adapter = eventsAdapter
        rvCalendarEvent.addItemDecoration(
            DividerItemDecoration(
                root.context,
                RecyclerView.VERTICAL
            )
        )

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()

        calendarView.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        calendarView.inDateStyle = InDateStyle.ALL_MONTHS
        calendarView.outDateStyle = OutDateStyle.END_OF_ROW
        calendarView.maxRowCount = 6
        calendarView.hasBoundaries=true

        calendarView.scrollToMonth(currentMonth)

        if (selectedDate == null) {
            calendarView.postDelayed({
                // Show today's events initially.
                selectDate(today)
            }, 500)
        }


        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view) {
                selectDate(it)
            }

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val dotView = container.dotView

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.visibility = View.VISIBLE
                    container.todayBg.visibility = View.GONE
                    textView.background = null
                    textView.setTypeface(null, Typeface.NORMAL)
                    textView.setTextColor(Color.parseColor("#000000"))
                    when (day.date) {
                        selectedDate -> {
                            textView.setTextColor(root.context.getColorCompat(R.color.calendar_blue))
                            if(day.date == today) container.todayBg.visibility = View.VISIBLE
                            textView.setTypeface(textView.typeface, Typeface.BOLD)
                            textView.setBackgroundResource(R.drawable.bg_calendar_day_selected)
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                        today -> {
                            container.todayBg.visibility = View.VISIBLE
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                        else -> {
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.visibility = View.VISIBLE
                    dotView.visibility = View.INVISIBLE
                    textView.background = null
                    textView.setTextColor(Color.parseColor("#40000000"))
                }
            }
        }

        calendarView.monthScrollListener = {
            tvCalendarMonth.text = TimeHelper.formatMmmYyyy(it.yearMonth)
            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }


        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text = daysOfWeek[index].name.first().toString()
                            tv.setTextColor(Color.parseColor("#000000"))
                        }
                }
            }
        }

//        setupEvent(currentMonth)

    }

    private fun FragmentCalendarEventBinding.selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { calendarView.notifyDateChanged(it) }
            calendarView.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun FragmentCalendarEventBinding.updateAdapterForDate(date: LocalDate) {
        eventsAdapter.events.clear()
        eventsAdapter.events.addAll(events[date].orEmpty())
        eventsAdapter.notifyDataSetChanged()
        tvCalendarSelectedDate.text = TimeHelper.formatdMmmYyyy(date)
    }

    class Holder: EpoxyHolder() {
        lateinit var binding: FragmentCalendarEventBinding
        override fun bindView(itemView: View) {
            binding = FragmentCalendarEventBinding.bind(itemView)
        }
    }
}