package unpad.fmipa.hifi.android.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_physics_dept_news.*
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.presentation.common.RssAdapter



class PhysicsDeptNewsFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main_physics_dept_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel

        var adapter: RssAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.itemAnimator = DefaultItemAnimator()
        viewModel.getArticleList()
            .observe(this, Observer { articles ->
                if (articles != null) {
                    // set adapter
                    println(articles.toString())

                    val shownArticle =
                        if (articles.size >= 4)
                            articles.take(4).toMutableList()
                        else
                            articles

                    adapter = RssAdapter(shownArticle)
                    recycler_view.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

            })
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFeed()
    }
}