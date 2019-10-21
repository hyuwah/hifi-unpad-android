package unpad.fmipa.hifi.android.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import kotlinx.android.synthetic.main.fragment_calendar_event.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.MonthDay
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.helpers.getColorCompat
import unpad.fmipa.hifi.android.ui.base.calendar.DayViewContainer
import unpad.fmipa.hifi.android.ui.base.calendar.MonthViewContainer
import unpad.fmipa.hifi.android.ui.model.CalendarEvent
import java.util.*

class EventCalendarFragment : Fragment() {

    private var state: Bundle? = null

    private lateinit var viewModel : EventCalendarViewModel

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private var events = mutableMapOf<LocalDate, List<CalendarEvent>>()

    private val eventsAdapter = CalendarEventsAdapter {
        Toast.makeText(requireContext(), "Click ${it.date}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calendar_event, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        state = savedInstanceState
        viewModel = ViewModelProvider(this).get(EventCalendarViewModel::class.java)
        setupCalendar(savedInstanceState)

        viewModel.events.observe(this, androidx.lifecycle.Observer {
            events = it
        })

        viewModel.fetch()
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { calendar_view.notifyDateChanged(it) }
            calendar_view.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun setupCalendar(savedInstanceState: Bundle?) {
        rv_calendar_event.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv_calendar_event.adapter = eventsAdapter
        rv_calendar_event.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()

        calendar_view.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        calendar_view.inDateStyle = InDateStyle.ALL_MONTHS
        calendar_view.outDateStyle = OutDateStyle.END_OF_ROW
        calendar_view.maxRowCount = 6
        calendar_view.hasBoundaries=true

        calendar_view.scrollToMonth(currentMonth)

        if (savedInstanceState == null) {
            calendar_view.post {
                // Show today's events initially.
                selectDate(today)
            }
        }


        calendar_view.dayBinder = object : DayBinder<DayViewContainer> {
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
                            textView.setTextColor(requireContext().getColorCompat(R.color.calendar_blue))
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
                    textView.setTextColor(Color.parseColor("#40000000"))
                }
            }
        }

        calendar_view.monthScrollListener = {
            tv_calendar_month.text =
                    titleFormatter.format(it.yearMonth)
//                (if (it.year == today.year) titleSameYearFormatter.format(it.yearMonth) else titleFormatter.format(
//                    it.yearMonth
//                ))

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }


        calendar_view.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
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

    private fun setupEvent(currentMonth: YearMonth) {
        var day1 = currentMonth.atDay(MonthDay.now().dayOfMonth)
        events[day1] = listOf(
            CalendarEvent(UUID.randomUUID().toString(), "Kamufi Event", day1),
            CalendarEvent(UUID.randomUUID().toString(), "Pelatihan Fiskom", day1)
        )
        updateAdapterForDate(day1)
        events[day1.plusDays(3)] = listOf(
            CalendarEvent(
                UUID.randomUUID().toString(), "PORFI: Dota 2",
                day1.plusDays(3)
            ),
            CalendarEvent(
                UUID.randomUUID().toString(), "Pendaftaran MAWAPRES",
                day1.plusDays(3)
            ),
            CalendarEvent(
                UUID.randomUUID().toString(), "Roadshow Calon Ketua BEM",
                day1.plusDays(3)
            )
        )
        updateAdapterForDate(day1.plusDays(3))
    }

    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.events.clear()
        eventsAdapter.events.addAll(events[date].orEmpty())
        eventsAdapter.notifyDataSetChanged()
        tv_calendar_selected_date.text = selectionFormatter.format(date)
    }
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
