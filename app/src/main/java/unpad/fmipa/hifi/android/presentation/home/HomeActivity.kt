package unpad.fmipa.hifi.android.presentation.home

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.ActivityMainBinding
import unpad.fmipa.hifi.android.helpers.ChromeCustomTabs

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    internal lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        with(binding) {

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
                toolbar.mainToolbar.setBackgroundColor(colorBackground)
                toolbar.tvTopbarTitle.setTextColor(colorText)
            }

            btnMainPaus.setOnClickListener {
                ChromeCustomTabs.create(this@HomeActivity).build()
                    .launchUrl(this@HomeActivity, Uri.parse(ChromeCustomTabs.PAUS_URL))
            }

            btnMainAngkutan.setOnClickListener {
                ChromeCustomTabs.create(this@HomeActivity).build()
                    .launchUrl(this@HomeActivity, Uri.parse(ChromeCustomTabs.ANGKUTAN_URL))
            }

            btnMainPintas.setOnClickListener {
                ChromeCustomTabs.create(this@HomeActivity).build()
                    .launchUrl(this@HomeActivity, Uri.parse(ChromeCustomTabs.PINTAS_URL))
            }
        }

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_main_hifi_menu,
                HimpunanMainMenuFragment()
            ).replace(
                R.id.fl_event_calendar,
                EventCalendarFragment()
            ).replace(
                R.id.fl_physics_dept_news,
                PhysicsDeptNewsFragment()
            )
            .commit()

        viewModel.snackbar.observe(this) { value ->
            value?.let {
                Snackbar.make(binding.rootView, value, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShowed()
            }
        }
    }

}


