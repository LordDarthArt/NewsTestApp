package tk.lorddarthart.newstestapp.app.view.fragment.news

import android.app.Fragment
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import tk.lorddarthart.newstestapp.app.NewsTestApp
import tk.lorddarthart.newstestapp.app.model.AppDatabase
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.app.model.response.Item
import tk.lorddarthart.newstestapp.app.model.response.News
import tk.lorddarthart.newstestapp.app.presenter.fragment.news.NewsFragmentPresenter
import tk.lorddarthart.newstestapp.app.view.base.BaseFragment
import tk.lorddarthart.newstestapp.app.view.fragment.news.adapter.NewsListAdapter
import tk.lorddarthart.newstestapp.databinding.NewsFragmentBinding
import tk.lorddarthart.newstestapp.utils.custom_view.CenterZoomLayoutManager
import tk.lorddarthart.newstestapp.utils.helper.CompareObjects
import tk.lorddarthart.newstestapp.utils.helper.PushNotifications
import tk.lorddarthart.newstestapp.utils.helper.logDebug
import tk.lorddarthart.newstestapp.utils.static_collections.Strings
import java.lang.Exception
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView {
    private lateinit var newsListAdapter: NewsListAdapter

    @Inject
    lateinit var app: NewsTestApp

    @InjectPresenter
    lateinit var newsPresenter: NewsFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        fragmentBinding = NewsFragmentBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun setupVariables() {

    }

    override fun initListeners() {

    }

    override fun start() {
        newsPresenter.getSomeNews()
    }

    override fun initializeAdapter(news: List<NewsEntity>) {
        with (fragmentBinding as NewsFragmentBinding) {
            newsListAdapter = NewsListAdapter(activity, news)
            newsList.adapter = newsListAdapter
            activity.setSupportActionBar(newsToolbar)
            newsList.layoutManager = CenterZoomLayoutManager(activity)
        }
        scrollToItem()
    }

    override fun scrollToItem() {
        // Скролл до нужного элемента
        if (NewsTestApp.notificationIntent?.getBooleanExtra("checked", false) == true) {
            NewsTestApp.notificationIntent?.getIntExtra("position", 0)?.let {
                (fragmentBinding as NewsFragmentBinding).newsList.smoothScrollToPosition(it)
            }
            val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(PushNotifications(activity).uniqueId)
            NewsTestApp.notificationIntent?.putExtra("checked", false)
        } else {
            // TODO: активировать CenterZoomToScroll для модификации отображения контента
        }
    }

    override fun showSnackBar() {
        Snackbar.make(fragmentBinding.root, "Произошла ошибка, проверьте соединение", Snackbar.LENGTH_LONG).show()
    }

    override fun showServerErrorSnackBar() {
        Snackbar.make(fragmentBinding.root, "Произошла ошибка сервера", Snackbar.LENGTH_LONG).show()
    }

    override fun addItem(item: NewsEntity) {
        Completable.create {
            AppDatabase.getInstance(activity).newsDao.addNewFeed(item)
        }.subscribeOn(Schedulers.newThread())
            .subscribe()
    }

    override fun getCurrentNews() {
        Single.create<MutableList<NewsEntity>> { completable ->
            try {
                val allNews = AppDatabase.getInstance(activity).newsDao.getNews()
                allNews.sortByDescending { it.newsDate }

                completable.onSuccess(allNews)
            } catch (e: Exception) {
                completable.onError(e)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ sortedNews ->
                initializeAdapter(sortedNews)
            }, {

            })
    }
}