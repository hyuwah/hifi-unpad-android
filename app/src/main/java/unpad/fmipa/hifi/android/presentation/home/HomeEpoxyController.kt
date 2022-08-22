package unpad.fmipa.hifi.android.presentation.home

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.prof.rssparser.Article
import unpad.fmipa.hifi.android.domain.model.CalendarEvent
import unpad.fmipa.hifi.android.presentation.home.akademik.AcademicMenuModel
import unpad.fmipa.hifi.android.presentation.home.akademik.academicMenu
import unpad.fmipa.hifi.android.presentation.home.events.EventCalendarModel
import unpad.fmipa.hifi.android.presentation.home.events.eventCalendar
import unpad.fmipa.hifi.android.presentation.home.himpunan.HimpunanMainMenu
import unpad.fmipa.hifi.android.presentation.home.himpunan.HimpunanMenuModel
import unpad.fmipa.hifi.android.presentation.home.himpunan.himpunanMenu
import unpad.fmipa.hifi.android.presentation.home.news.PhysicsDeptNewsModel
import unpad.fmipa.hifi.android.presentation.home.news.physicsDeptNews
import java.time.LocalDate

class HomeEpoxyController(
    private val listener: Listener
) : EpoxyController(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(), EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    private var newsArticles: List<Article>? = null
    private var calendarEvents: Map<LocalDate, List<CalendarEvent>>? = null

    override fun buildModels() {
        val c = this@HomeEpoxyController

        academicMenu {
            id(AcademicMenuModel.ID)
        }

        himpunanMenu {
            id(HimpunanMenuModel.ID)
            itemClickListener {
                c.listener.onHimpunanMenuClicked(it)
            }
        }

        if (calendarEvents != null) {
            eventCalendar {
                id(EventCalendarModel.ID)
                events(this@HomeEpoxyController.calendarEvents!!)
            }
        }

        if (newsArticles != null) {
            physicsDeptNews {
                id(PhysicsDeptNewsModel.ID)
                articles(c.newsArticles)
            }
        }
    }

    fun submitNewsData(articles: List<Article>) {
        newsArticles = articles
        requestModelBuild()
    }

    fun submitCalendarEvents(events: Map<LocalDate, List<CalendarEvent>>) {
        calendarEvents = events
        requestModelBuild()
    }

    interface Listener {
        fun onHimpunanMenuClicked(item: HimpunanMainMenu)
    }

}