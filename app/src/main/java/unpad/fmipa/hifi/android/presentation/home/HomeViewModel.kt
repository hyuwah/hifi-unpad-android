package unpad.fmipa.hifi.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

        //private val url = "https://news.mit.edu/rss/topic/physics"
        private val url = "https://www.unpad.ac.id/pengumuman/feed/"

        private val viewModelJob = Job()
        private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        private lateinit var articleListLive: MutableLiveData<MutableList<Article>>

        private val _snackbar = MutableLiveData<String?>()

        val snackbar: LiveData<String?>
            get() = _snackbar

        fun onSnackbarShowed() {
            _snackbar.value = null
        }

        fun getArticleList(): MutableLiveData<MutableList<Article>> {
            if (!::articleListLive.isInitialized) {
                articleListLive = MutableLiveData()
            }
            return articleListLive
        }

        private fun setArticleList(articleList: MutableList<Article>) {
            articleListLive.postValue(articleList)
        }

        override fun onCleared() {
            super.onCleared()
            viewModelJob.cancel()
        }

        fun fetchFeed() {
            coroutineScope.launch(Dispatchers.Main) {
                try {
                    val parser = Parser.Builder()
                        .build()
                    val articleList = parser.getChannel(url).articles
                    setArticleList(articleList)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _snackbar.value = "An error has occurred. Please retry"
                    setArticleList(mutableListOf())
                }
            }
        }
    }