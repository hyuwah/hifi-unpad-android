package unpad.fmipa.hifi.android.presentation.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prof.rssparser.Article
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.ItemNewsRssMainBinding
import unpad.fmipa.hifi.android.presentation.common.NewsDetailActivity.Companion.CONTENT_KEY
import unpad.fmipa.hifi.android.presentation.common.NewsDetailActivity.Companion.TITLE_KEY
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RssAdapter(private val articles: MutableList<Article>) : RecyclerView.Adapter<RssAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNewsRssMainBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(articles[position])

    inner class ViewHolder(private val binding: ItemNewsRssMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) = with(binding) {
            val pubDateString = try {
                val sourceDateString = article.pubDate

                val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                val date = sourceSdf.parse(sourceDateString)

                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                sdf.format(date)

            } catch (e: ParseException) {
                e.printStackTrace()
                article.pubDate
            }

            title.text = article.title

            image.load(article.image) {
                placeholder(R.drawable.ic_launcher_background)
            }

            pubDate.text = pubDateString

            var categories = ""
            for (i in 0 until article.categories.size) {
                categories = if (i == article.categories.size - 1) {
                    categories + article.categories[i]
                } else {
                    categories + article.categories[i] + ", "
                }
            }

            tvCategories.text = categories

            root.setOnClickListener {
                var intent = Intent(it.context,NewsDetailActivity::class.java).apply {
                    putExtra(TITLE_KEY,article.title)
                    putExtra(CONTENT_KEY,article.content)
                }
                it.context.startActivity(intent)
            }
        }
    }
}