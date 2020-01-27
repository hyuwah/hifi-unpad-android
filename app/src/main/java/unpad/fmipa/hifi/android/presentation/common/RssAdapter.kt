package unpad.fmipa.hifi.android.presentation.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news_rss_main.view.*
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.presentation.common.NewsDetailActivity.Companion.CONTENT_KEY
import unpad.fmipa.hifi.android.presentation.common.NewsDetailActivity.Companion.TITLE_KEY
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RssAdapter(private val articles: MutableList<Article>) : RecyclerView.Adapter<RssAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_news_rss_main, parent, false))

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(articles[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {


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

            itemView.title.text = article.title

            Picasso.get()
                .load(article.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.image)

            itemView.pubDate.text = pubDateString

            var categories = ""
            for (i in 0 until article.categories.size) {
                categories = if (i == article.categories.size - 1) {
                    categories + article.categories[i]
                } else {
                    categories + article.categories[i] + ", "
                }
            }

            itemView.categories.text = categories

            itemView.setOnClickListener {
                var intent = Intent(it.context,NewsDetailActivity::class.java).apply {
                    putExtra(TITLE_KEY,article.title)
                    putExtra(CONTENT_KEY,article.content)
                }
                it.context.startActivity(intent)
            }
        }
    }
}