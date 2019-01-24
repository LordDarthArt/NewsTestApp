package tk.lorddarthart.newstestapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpServiceHelper {
    private lateinit var mInstance: HttpServiceHelper
    private val BASE_URL = "api.lenta.ru"
    private lateinit var mRetrofit: Retrofit

    internal fun HttpServiceHelper(): HttpServiceHelper {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return HttpServiceHelper()
    }

    fun getInstance(): HttpServiceHelper {
        return mInstance
    }

    fun getJSONApi(): JSONPlaceHolderApi {
        return mRetrofit.create(JSONPlaceHolderApi::class.java)
    }
}