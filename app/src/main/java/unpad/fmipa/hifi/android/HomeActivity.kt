package unpad.fmipa.hifi.android

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_day.view.*
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.calendar_event_item_view.view.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.MonthDay
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import unpad.fmipa.hifi.android.helpers.ChromeCustomTabs
import java.util.*

class HomeActivity : AppCompatActivity() {

    var state : Bundle? = null
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = savedInstanceState
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        scrollContent.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            val colorBackground = if (scrollY < 255) {
                Color.argb(scrollY, 222, 36, 24)
            } else {
                Color.argb(255, 222, 36, 24)
            }
            val colorText = if (scrollY < 255) {
                Color.argb(scrollY, 255, 255, 255)
            } else {
                Color.argb(255, 255, 255, 255)
            }
            main_toolbar.setBackgroundColor(colorBackground)
            tv_topbar_title.setTextColor(colorText)

        }

        btn_main_paus.setOnClickListener {
            ChromeCustomTabs.create(this).build().launchUrl(this, Uri.parse(ChromeCustomTabs.PAUS_URL))
        }

        btn_main_angkutan.setOnClickListener {
            ChromeCustomTabs.create(this).build().launchUrl(this, Uri.parse(ChromeCustomTabs.ANGKUTAN_URL))
        }

        btn_main_pintas.setOnClickListener {
            ChromeCustomTabs.create(this).build().launchUrl(this, Uri.parse(ChromeCustomTabs.PINTAS_URL))
        }

        supportFragmentManager.beginTransaction().replace(R.id.fl_main_hifi_profile, HimpunanMainMenuFragment())
            .commit()

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getArticleList().observe(this, Observer { articles ->

            if (articles != null) {
               println(articles.toString())
            }

        })

        viewModel.snackbar.observe(this, Observer { value ->
            value?.let {
                Snackbar.make(rootView, value, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShowed ()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        setupCalendar(state)
        viewModel.fetchFeed()
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

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()

    private val eventsAdapter = CalendarEventsAdapter {
        Toast.makeText(this,"Click ${it.date}", Toast.LENGTH_SHORT).show()
    }
    // METHOD CALENDAR
    fun setupCalendar(savedInstanceState: Bundle?){
        rv_calendar_event.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_calendar_event.adapter = eventsAdapter
        rv_calendar_event.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()

        calendar_view.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
        calendar_view.scrollToMonth(currentMonth)

        if (savedInstanceState == null) {
            calendar_view.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val textView = view.exThreeDayText
            val dotView = view.exThreeDotView

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }
        calendar_view.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val dotView = container.dotView

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.visibility = View.VISIBLE
                    when (day.date) {
                        today -> {
                            textView.setTextColor(Color.parseColor("#00bb00"))
                            textView.setBackgroundColor(Color.parseColor("#cdcdcd"))
                            dotView.visibility = View.INVISIBLE
                        }
                        selectedDate -> {
                            textView.setTextColor(Color.parseColor("#0000aa"))
                            textView.setBackgroundColor(Color.parseColor("#aa0000"))
                            dotView.visibility = View.INVISIBLE
                        }
                        else -> {
                            textView.setTextColor(Color.parseColor("#000000"))
                            textView.background = null
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                    dotView.visibility = View.INVISIBLE
                }
            }
        }

        calendar_view.monthScrollListener = {
            this.title = if (it.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legendLayout
        }
        calendar_view.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.setTextColor(Color.parseColor("#000000"))
                    }
                }
            }
        }

        setupEvent(currentMonth)

    }

    private fun setupEvent(currentMonth:YearMonth){
        var day1 = currentMonth.atDay(MonthDay.now().dayOfMonth)
        events[day1] = listOf(
            Event(UUID.randomUUID().toString(),"Event 1",day1),
            Event(UUID.randomUUID().toString(),"Event 11",day1),
            Event(UUID.randomUUID().toString(),"Event 12",day1),
            Event(UUID.randomUUID().toString(),"Event 15",day1)
        )
        updateAdapterForDate(day1)
        events[day1.plusDays(3)] = listOf(
            Event(UUID.randomUUID().toString(),"Event 1",day1.plusDays(3)),
            Event(UUID.randomUUID().toString(),"Event 11",day1.plusDays(3)),
            Event(UUID.randomUUID().toString(),"Event 15",day1.plusDays(3))
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

/** TEST CALENDAR **/

private val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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

data class Event(val id: String, val text: String, val date: LocalDate)

class CalendarEventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder>() {

    val events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.calendar_event_item_view,parent,false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {
                onClick(events[adapterPosition])
            }
        }

        fun bind(event: Event) {
            itemView.itemEventText.text = event.text
        }
    }

}
