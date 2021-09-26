package unpad.fmipa.hifi.android.presentation.common

import android.os.Bundle
import android.view.MenuItem
import android.viewbinding.library.activity.viewBinding
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import unpad.fmipa.hifi.android.databinding.ActivityNewsDetailBinding


class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val TITLE_KEY = "news_title"
        const val CONTENT_KEY = "news_content"
    }

    private val binding: ActivityNewsDetailBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            setSupportActionBar(myToolbar)
            myToolbarTitle.text = "News:" + " ${intent.getStringExtra(TITLE_KEY)}"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val content = intent.getStringExtra(CONTENT_KEY)

            wvNewsDetail.apply {
                settings.loadWithOverviewMode = true
                settings.javaScriptEnabled = true
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false
                webChromeClient = WebChromeClient()
                loadDataWithBaseURL(
                    null,
                    "<style>img{display: inline; height: auto; max-width: 100%;} " +
                            "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" +
                            content, null, "utf-8", null
                )
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
