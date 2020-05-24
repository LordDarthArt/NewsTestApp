package tk.lorddarthart.newstestapp.app.view.fragment.news

import com.arellomobile.mvp.MvpView
import retrofit2.Response
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.app.model.response.News

interface NewsView : MvpView {
    fun initializeAdapter(news: List<NewsEntity>)
    fun scrollToItem()
    fun showSnackBar()
    fun showServerErrorSnackBar()
    fun addItem(item: NewsEntity)
    fun getCurrentNews()
}