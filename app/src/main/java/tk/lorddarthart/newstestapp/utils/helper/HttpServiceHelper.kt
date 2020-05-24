package tk.lorddarthart.newstestapp.utils.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tk.lorddarthart.newstestapp.app.model.request.NetworkRequest
import tk.lorddarthart.newstestapp.utils.static_collections.Strings
import java.util.concurrent.TimeUnit

object HttpServiceHelper {
    private val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .callTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Strings.NEWS_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val jsonApi: NetworkRequest
        get() {
            return retrofit.create(NetworkRequest::class.java)
        }
}