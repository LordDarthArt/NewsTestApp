package tk.lorddarthart.newstestapp.utils.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tk.lorddarthart.newstestapp.app.model.request.NetworkRequest

class HttpServiceHelper {
    private val baseUrl = "https://api.lenta.ru"
    private var retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): HttpServiceHelper {
        return HttpServiceHelper()
    }

    fun getJSONApi(): NetworkRequest {
        return retrofit.create(NetworkRequest::class.java)
    }

    companion object {
        var INSTANCE: HttpServiceHelper? = null
    }
}