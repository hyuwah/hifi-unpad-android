package unpad.fmipa.hifi.android.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import unpad.fmipa.hifi.android.databinding.ActivityMainBinding
import unpad.fmipa.hifi.android.presentation.home.events.EventCalendarViewModel
import unpad.fmipa.hifi.android.presentation.home.himpunan.HimpunanMainMenu

class HomeActivity : AppCompatActivity(), HomeEpoxyController.Listener {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private val calendarViewModel: EventCalendarViewModel by viewModel()

    private val controller = HomeEpoxyController(this)

    private var scrollY = 0
    private var rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            scrollY += dy
            setupToolbarColor()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            mainRecyclerview.addOnScrollListener(rvScrollListener)
            mainRecyclerview.setControllerAndBuildModels(controller)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.snackbar.collectLatest(::showSnackbar)
        }
        viewModel.articleListLive.observe(this, controller::submitNewsData)
        calendarViewModel.events.observe(this, controller::submitCalendarEvents)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFeed()
        calendarViewModel.fetch()
    }

    override fun onDestroy() {
        binding.mainRecyclerview.removeOnScrollListener(rvScrollListener)
        super.onDestroy()
    }

    override fun onHimpunanMenuClicked(item: HimpunanMainMenu) {
        Toast.makeText(this, "${item.title} clicked", Toast.LENGTH_SHORT).show()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setupToolbarColor() = with(binding.toolbar) {
        mainToolbar.setBackgroundColor(Color.argb(scrollY.coerceIn(0, 255), 222, 36, 24))
        tvTopbarTitle.setTextColor(Color.argb(scrollY.coerceIn(0, 255), 255, 255, 255))
    }

}


