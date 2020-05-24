package tk.lorddarthart.newstestapp.app.presenter.fragment.news

import android.accounts.NetworkErrorException
import com.arellomobile.mvp.InjectViewState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.app.model.response.Item
import tk.lorddarthart.newstestapp.app.model.response.News
import tk.lorddarthart.newstestapp.app.presenter.base.fragment.BaseFragmentPresenter
import tk.lorddarthart.newstestapp.app.receiver.ConnectivityChangeReceiver
import tk.lorddarthart.newstestapp.app.view.fragment.news.NewsView
import tk.lorddarthart.newstestapp.utils.helper.HttpServiceHelper
import tk.lorddarthart.newstestapp.utils.static_collections.Strings

@InjectViewState
class NewsFragmentPresenter: BaseFragmentPresenter<NewsView>() {
    var state: Boolean = false

    fun getSomeNews() {
        try {
            HttpServiceHelper.jsonApi.getNews()
                .enqueue(object : Callback<News> {
                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        val news = response.body()
                        if (news != null && news.item!!.isNotEmpty()) {
                            val sortedList = (news.item as MutableList<Item>)
                            sortedList.forEach { item ->
                                viewState.addItem(
                                    NewsEntity(
                                        newsDate = item.info.modified!!,
                                        newsTitle = item.info.title ?: Strings.ERROR,
                                        newsDesc = item.info.rightcol ?: Strings.ERROR,
                                        newsPic = item.title_image!!.url,
                                        newsPicDesc = item.title_image!!.credits,
                                        newsRubric = item.rubric!!.title
                                    )
                                )
                            }

                            getCurrentNews()
                        } else {
                            getCurrentNews()
                            viewState.scrollToItem()
                        }
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        getCurrentNews()
                        viewState.scrollToItem()
                        viewState.showServerErrorSnackBar()
                    }
                })
        } catch (e: NetworkErrorException) {
            getCurrentNews()
            viewState.scrollToItem()
            viewState.showSnackBar()
        } catch (e: Exception) {
            getCurrentNews()
            viewState.scrollToItem()
            e.printStackTrace()
        }
    }

    fun getCurrentNews() {
        viewState.getCurrentNews()
    }
}