package tk.lorddarthart.newstestapp.app.model.request

import retrofit2.Call
import retrofit2.http.GET
import tk.lorddarthart.newstestapp.app.model.response.News

interface NetworkRequest {
    @GET("lists/latest")
    fun getNews(): Call<News>
}