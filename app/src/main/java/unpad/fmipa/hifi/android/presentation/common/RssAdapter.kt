package unpad.fmipa.hifi.android.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prof.rssparser.Article
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.ItemNewsRssMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RssAdapter(
    private val articles: List<Article>,
    private val onItemClickListener: (article: Article) -> Unit
) : RecyclerView.Adapter<RssAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNewsRssMainBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onItemClickListener
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(articles[position])

    inner class ViewHolder(
        private val binding: ItemNewsRssMainBinding,
        private val onItemClickListener: (article: Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
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
            tvCategories.text = article.categories.joinToString { it }

            root.setOnClickListener {
                onItemClickListener(article)
            }
        }
    }
}