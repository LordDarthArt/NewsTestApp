package tk.lorddarthart.newstestapp

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

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
        view = window.decorView.rootView
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        databaseHelper =
            DatabaseHelper(applicationContext, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION)
        sqliteDatabase = databaseHelper.writableDatabase
        val query: String =
            "SELECT " + DatabaseHelper.NEWS_ID + ", " + DatabaseHelper.NEWS_DATE + ", " + DatabaseHelper.NEWS_TITLE + ", " + DatabaseHelper.NEWS_DESC + ", " + DatabaseHelper.NEWS_RUBRIC + " , " + DatabaseHelper.NEWS_PIC + ", " + DatabaseHelper.NEWS_PICDESC + " FROM " + DatabaseHelper.TABLE_NEWS
        cursor = sqliteDatabase.rawQuery(query, Array(0) { "" })
        recyclerView = findViewById(R.id.recyclerView)
        linlayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linlayoutManager
        if (sharedPreferences.contains("rubrics")) {

        } else {
            try { // Попытка получить данные
                if (!ConnectivityChangeReciever().checkInternet()) { // Если соединение отсутствует, ошибка
                    throw NetworkErrorException()
                }
                HttpServiceHelper().getInstance().getJSONApi().getNews()
                    .enqueue(object:Callback<News> {

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                            val news = response.body()
                            if (news != null && news.item.isNotEmpty()) {
                                for (i: Int in 0 until news.item.size) {
                                    DatabaseHelper.addNews(sqliteDatabase, news.item[i].info.modified,
                                        news.item[i].info.title, news.item[i].info.rightcol,
                                        news.item[i].title_image!!.url, news.item[i].title_image!!.credits,
                                        news.item[i].rubric.title)
                                }
                                recyclerViewAdapter = RecyclerViewAdapter(
                                    applicationContext, news.item
                                )
                                recyclerView.adapter = recyclerViewAdapter
                            } else {
                                getCurrentNews()
                            }
                        }

                        override fun onFailure(call: Call<News>, t: Throwable) {
                            getCurrentNews()
                            Snackbar.make(window.decorView.rootView, "Произошла ошибка сервера", Snackbar.LENGTH_LONG).show()
                        }
                    })
            } catch (e: NetworkErrorException) {
                getCurrentNews()
                Snackbar.make(window.decorView.findViewById(android.R.id.content), "Произошла ошибка, проверьте соединение", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                getCurrentNews()
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        netReceiver = ConnectivityChangeReciever()
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val intent = registerReceiver(netReceiver, netFilter)
        if (intent != null) {

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
            item.rubric.title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_RUBRIC))
            item.title_image!!.url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_PIC))
            item.title_image!!.credits = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_PICDESC))
            news.add(item)
        }
        initializeAdapter()
    }

    private fun initializeAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter(applicationContext, news)
        recyclerView.adapter = recyclerViewAdapter
    }

    internal inner class ConnectivityChangeReciever : BroadcastReceiver() {

        @SuppressLint("UnsafeProtectedBroadcastReceiver")
        override fun onReceive(context: Context, intent: Intent) {
            val snackBarNotifications = SnackBarNotifications()
            if (!checkInternet()) {
                if (!state) {
                    snackBarNotifications.noInternerConstraintLayout(view)
                    state = true
                }
            } else {
                if (state) {
                    snackBarNotifications.onInternetRestoreConstraintLayout(view)
                    try { // Попытка получить данные
                        if (!ConnectivityChangeReciever().checkInternet()) { // Если соединение отсутствует, ошибка
                            throw NetworkErrorException()
                        }
                        HttpServiceHelper().getInstance().getJSONApi().getNews()
                            .enqueue(object:Callback<News> {

                                override fun onResponse(call: Call<News>, response: Response<News>) {
                                    val news = response.body()
                                    if (news != null && news.item.isNotEmpty()) {
                                        for (i: Int in 0 until news.item.size) {
                                            DatabaseHelper.addNews(sqliteDatabase, news.item[i].info.modified,
                                                news.item[i].info.title, news.item[i].info.rightcol,
                                                news.item[i].title_image!!.url, news.item[i].title_image!!.credits,
                                                news.item[i].rubric.title)
                                        }
                                        recyclerViewAdapter = RecyclerViewAdapter(
                                            applicationContext, news.item
                                        )
                                        recyclerView.adapter = recyclerViewAdapter
                                    } else {
                                        getCurrentNews()
                                    }
                                }

                                override fun onFailure(call: Call<News>, t: Throwable) {
                                    getCurrentNews()
                                    Snackbar.make(window.decorView.rootView, "Произошла ошибка сервера", Snackbar.LENGTH_LONG).show()
                                }
                            })
                    } catch (e: NetworkErrorException) {
                        getCurrentNews()
                        Snackbar.make(window.decorView.findViewById(android.R.id.content), "Произошла ошибка, проверьте соединение", Snackbar.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        getCurrentNews()
                        e.printStackTrace()
                    }
                    state = false
                }
            }
        }

        internal fun checkInternet(): Boolean {
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
}
