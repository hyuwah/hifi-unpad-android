package unpad.fmipa.hifi.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

        //private val url = "https://news.mit.edu/rss/topic/physics"
        private val url = "https://www.unpad.ac.id/pengumuman/feed/"

        private var _articleListLive: MutableLiveData<List<Article>> = MutableLiveData()
        var articleListLive: LiveData<List<Article>> = _articleListLive

        private val _snackbar = MutableSharedFlow<String>()
        val snackbar: SharedFlow<String> = _snackbar

        fun fetchFeed() {
            viewModelScope.launch {
                try {
                    val parser = Parser.Builder()
                        .build()
                    val articleList = parser.getChannel(url).articles
                    _articleListLive.postValue(articleList)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _snackbar.emit("An error has occurred. Please retry")
                    _articleListLive.postValue(listOf())
                }
            }
        }
    }