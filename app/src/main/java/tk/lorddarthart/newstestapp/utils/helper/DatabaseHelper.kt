package tk.lorddarthart.newstestapp.utils.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version), BaseColumns {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE_NEWS_SCRIPT)
        db.execSQL(DATABASE_CREATE_NEWS_RUBRIC_SCRIPT)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }

    companion object {
        const val DATABASE_NAME = "tk.lorddarthart.newstestapp.db"
        var DATABASE_VERSION = 1

        const val TABLE_NEWS = "news"
        const val NEWS_ID = "news_id"
        const val NEWS_TITLE = "news_title"
        const val NEWS_RUBRIC = "news_rubric"
        const val NEWS_DESC = "news_desc"
        const val NEWS_PIC = "news_pic"
        const val NEWS_PICDESC = "news_picdesc"
        const val NEWS_DATE = "news_date"

        const val TABLE_RUBRICS = "rubrics"
        const val RUBRIC_ID = "city_id"
        const val RUBRIC_NAME = "city_name"

        const val DATABASE_CREATE_NEWS_RUBRIC_SCRIPT = ("create table "
                + TABLE_RUBRICS
                + " (" + RUBRIC_ID + " integer not null primary key autoincrement, "
                + RUBRIC_NAME + " text not null, "
                + "UNIQUE(" + RUBRIC_NAME + ") ON CONFLICT REPLACE);")

        const val DATABASE_CREATE_NEWS_SCRIPT = ("create table "
                + TABLE_NEWS
                + " (" + NEWS_ID + " integer not null primary key autoincrement, "
                + NEWS_TITLE + " text not null, "
                + NEWS_DATE + " long not null, "
                + NEWS_RUBRIC + " text not null, "
                + NEWS_DESC + " text not null, "
                + NEWS_PIC + " text, "
                + NEWS_PICDESC + " text, "
                + "UNIQUE(" + NEWS_DATE + ") ON CONFLICT REPLACE);")

        fun addNews(
            mSqLiteDatabase: SQLiteDatabase,
            news_date: Long?,
            news_title: String,
            news_desc: String,
            news_pic: String,
            news_picdesc: String,
            news_rubric: String
        ) {

            val newValues = ContentValues()
            newValues.put(NEWS_DATE, news_date)
            newValues.put(NEWS_TITLE, news_title)
            newValues.put(NEWS_DESC, news_desc)
            newValues.put(NEWS_RUBRIC, news_rubric)
            newValues.put(NEWS_PIC, news_pic)
            newValues.put(NEWS_PICDESC, news_picdesc)

            mSqLiteDatabase.insertWithOnConflict(
                TABLE_NEWS,
                null,
                newValues,
                SQLiteDatabase.CONFLICT_IGNORE
            )
        }

        fun addRubric(
            mSqLiteDatabase: SQLiteDatabase,
            rubric_id: Int,
            rubric_name: String
        ) {

            val newValues = ContentValues()
            newValues.put(RUBRIC_ID, rubric_id)
            newValues.put(RUBRIC_NAME, rubric_name)

            mSqLiteDatabase.insertWithOnConflict(
                TABLE_RUBRICS,
                null,
                newValues,
                SQLiteDatabase.CONFLICT_IGNORE
            )
        }
    }
}
