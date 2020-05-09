package tk.lorddarthart.newstestapp.app.view

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import android.content.Intent
import tk.lorddarthart.newstestapp.*
import tk.lorddarthart.newstestapp.app.model.response.Item
import tk.lorddarthart.newstestapp.app.model.response.News
import tk.lorddarthart.newstestapp.app.view.adapter.RecyclerViewAdapter
import tk.lorddarthart.newstestapp.utils.custom_view.CenterZoomLayoutManager
import tk.lorddarthart.newstestapp.utils.helper.*

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sqliteDatabase: SQLiteDatabase
    private lateinit var cursor: Cursor
    private var news: LinkedList<Item> = LinkedList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var linlayoutManager: RecyclerView.LayoutManager
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var view: View
    private lateinit var netReceiver: ConnectivityChangeReciever
    private var state: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        view = window.decorView.rootView
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        databaseHelper =
            DatabaseHelper(
                applicationContext,
                DatabaseHelper.DATABASE_NAME,
                null,
                DatabaseHelper.DATABASE_VERSION
            )
        sqliteDatabase = databaseHelper.writableDatabase
        val query: String =
            "SELECT " + DatabaseHelper.NEWS_ID + ", " + DatabaseHelper.NEWS_DATE + ", " + DatabaseHelper.NEWS_TITLE + ", " + DatabaseHelper.NEWS_DESC + ", " + DatabaseHelper.NEWS_RUBRIC + " , " + DatabaseHelper.NEWS_PIC + ", " + DatabaseHelper.NEWS_PICDESC + " FROM " + DatabaseHelper.TABLE_NEWS
        cursor = sqliteDatabase.rawQuery(query, Array(0) { "" })
        linlayoutManager =
            CenterZoomLayoutManager(
                this
            )
        recyclerView.layoutManager = linlayoutManager
        if (sharedPreferences.contains("rubrics")) {
            // TODO: добавить возможность выбора предпочитаемых рубрик
        } else {
            getSomeNews()
        }
    }

    override fun onResume() {
        super.onResume()
        netReceiver = ConnectivityChangeReciever()
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val intent = registerReceiver(netReceiver, netFilter)
        if (intent!=null) {
            // Выполнить действие
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(netReceiver)
    }

    fun getCurrentNews() {
        // Получаем объект новости из бд
        cursor.moveToFirst()
        cursor.moveToPrevious()
        news.clear()
        while (cursor.moveToNext()) {
            val item = Item()
            item.info.title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_TITLE))
            item.info.rightcol = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_DESC))
            item.info.modified = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.NEWS_DATE))
            item.rubric?.title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_RUBRIC))
            item.title_image!!.url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_PIC))
            item.title_image!!.credits = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_PICDESC))
            news.add(item)
        }
        news.sortWith(CompareObjects)
        initializeAdapter(news)
    }

    private fun initializeAdapter(news: List<Item>) {
        recyclerViewAdapter =
            RecyclerViewAdapter(
                applicationContext,
                news,
                recyclerView
            )
        recyclerView.adapter = recyclerViewAdapter
    }

    internal inner class ConnectivityChangeReciever : BroadcastReceiver() {
        // Проверяет доступ к интернету в режиме реального времени

        @SuppressLint("UnsafeProtectedBroadcastReceiver")
        override fun onReceive(context: Context, intent: Intent) {
            val snackBarNotifications =
                SnackBarNotifications()
            if (!checkInternet()) {
                if (!state) {
                    snackBarNotifications.noInternerConstraintLayout(view)
                    state = true
                }
            } else {
                if (state) {
                    snackBarNotifications.onInternetRestoreConstraintLayout(view)
                    getSomeNews()
                    state = false
                }
            }
        }

        internal fun checkInternet(): Boolean {
            // Проверка доступа к интернету
            val runtime = Runtime.getRuntime()
            try {
                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                val exitValue = ipProcess.waitFor()
                return exitValue == 0
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return false
        }
    }

    fun getSomeNews() {
        try { // Попытка получить данные с сервера
            if (!ConnectivityChangeReciever().checkInternet()) { // Если соединение отсутствует, ошибка
                throw NetworkErrorException()
            }
            HttpServiceHelper().getInstance().getJSONApi().getNews()
                .enqueue(object : Callback<News> {

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        val news = response.body()
                        if (news != null && news.item.isNotEmpty()) {
                            for (i: Int in 0 until news.item.size) {
                                DatabaseHelper.addNews(
                                    sqliteDatabase,
                                    news.item[i].info.modified,
                                    news.item[i].info.title,
                                    news.item[i].info.rightcol,
                                    news.item[i].title_image!!.url,
                                    news.item[i].title_image!!.credits,
                                    news.item[i].rubric!!.title
                                )
                            }
                            initializeAdapter(news.item)
                            scrollToItem()
                        } else {
                            getCurrentNews()
                            scrollToItem()
                        }
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        getCurrentNews()
                        scrollToItem()
                        Snackbar.make(window.decorView.rootView, "Произошла ошибка сервера", Snackbar.LENGTH_LONG)
                            .show()
                    }
                })
        } catch (e: NetworkErrorException) {
            getCurrentNews()
            scrollToItem()
            Snackbar.make(
                window.decorView.findViewById(android.R.id.content),
                "Произошла ошибка, проверьте соединение",
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            getCurrentNews()
            scrollToItem()
            e.printStackTrace()
        }
    }

    fun scrollToItem() {
        // Скролл до нужного элемента
        if (intent.getBooleanExtra("checked", false)) {
            recyclerView.post { recyclerView.smoothScrollToPosition(intent.getIntExtra("position", 0)) }
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(PushNotifications().uniqueId)
            intent.putExtra("checked", false)
        } else {
            // TODO: активировать CenterZoomToScroll для модификации отображения контента
        }
    }
}
