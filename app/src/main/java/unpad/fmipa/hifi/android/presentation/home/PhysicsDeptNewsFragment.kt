package unpad.fmipa.hifi.android.presentation.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.FragmentMainPhysicsDeptNewsBinding
import unpad.fmipa.hifi.android.presentation.common.RssAdapter


class PhysicsDeptNewsFragment : Fragment(R.layout.fragment_main_physics_dept_news) {

    private val binding: FragmentMainPhysicsDeptNewsBinding by viewBinding()
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel

        with(binding) {
            var adapter: RssAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.itemAnimator = DefaultItemAnimator()
            viewModel.getArticleList()
                .observe(viewLifecycleOwner) { articles ->
                    if (articles != null) {
                        // set adapter
                        println(articles.toString())

                        val shownArticle =
                            if (articles.size >= 4)
                                articles.take(4).toMutableList()
                            else
                                articles

                        adapter = RssAdapter(shownArticle)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFeed()
    }
}