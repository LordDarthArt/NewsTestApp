package tk.lorddarthart.newstestapp

import retrofit2.Call
import retrofit2.http.GET

interface JSONPlaceHolderApi {
    @GET("/lists/latest")
    fun getNews(): Call<News>
}