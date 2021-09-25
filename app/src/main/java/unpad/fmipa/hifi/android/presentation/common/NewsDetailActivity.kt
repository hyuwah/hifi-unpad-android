package unpad.fmipa.hifi.android.presentation.common

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_news_detail.*
import unpad.fmipa.hifi.android.R


class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val TITLE_KEY = "news_title"
        const val CONTENT_KEY = "news_content"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        setSupportActionBar(my_toolbar)
        my_toolbar_title.text = "News:" + " ${intent.getStringExtra(TITLE_KEY)}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var content = intent.getStringExtra(CONTENT_KEY)

        wv_news_detail.apply {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
