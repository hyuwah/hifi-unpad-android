package unpad.fmipa.hifi.android.presentation.home.news

import android.content.Intent
import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.prof.rssparser.Article
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.FragmentMainPhysicsDeptNewsBinding
import unpad.fmipa.hifi.android.presentation.common.NewsDetailActivity
import unpad.fmipa.hifi.android.presentation.common.RssAdapter

@EpoxyModelClass(layout = R.layout.fragment_main_physics_dept_news)
abstract class PhysicsDeptNewsModel: EpoxyModelWithHolder<PhysicsDeptNewsModel.Holder>() {

    companion object {
        const val ID = "PhysicsDeptNewsModel"
    }

    @EpoxyAttribute
    open var articles: List<Article>? = null

    override fun getDefaultLayout() = R.layout.fragment_main_physics_dept_news

    override fun createNewHolder(parent: ViewParent) = Holder()

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.binding) {
            articles?.let { articles ->
                // set adapter
                println(articles.toString())

                val shownArticle =
                    if (articles.size >= 4)
                        articles.take(4).toMutableList()
                    else
                        articles

                val adapter = RssAdapter(shownArticle) { article ->
                    val intent = Intent(root.context, NewsDetailActivity::class.java).apply {
                        putExtra(NewsDetailActivity.TITLE_KEY, article.title)
                        putExtra(NewsDetailActivity.CONTENT_KEY, article.content)
                    }
                    root.context.startActivity(intent)
                }
                recyclerView.layoutManager = LinearLayoutManager(root.context)
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.binding.recyclerView.adapter = null
    }

    class Holder: EpoxyHolder() {
        lateinit var binding: FragmentMainPhysicsDeptNewsBinding
        override fun bindView(itemView: View) {
            binding = FragmentMainPhysicsDeptNewsBinding.bind(itemView)
        }

    }
}