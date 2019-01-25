package tk.lorddarthart.newstestapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpServiceHelper {
    private lateinit var mInstance: HttpServiceHelper
    private val baseUrl = "https://api.lenta.ru"
    private var mRetrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
        mRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): HttpServiceHelper {
        return HttpServiceHelper()
    }

    fun getJSONApi(): JSONPlaceHolderApi {
        return mRetrofit.create(JSONPlaceHolderApi::class.java)
    }
}