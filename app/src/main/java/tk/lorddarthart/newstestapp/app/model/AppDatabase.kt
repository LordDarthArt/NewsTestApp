package tk.lorddarthart.newstestapp.app.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tk.lorddarthart.newstestapp.app.model.dao.NewsDao
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity

private const val DATABASE_VERSION = 1

@Database(entities = [NewsEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

        private const val DATABASE_NAME = "tk.lorddarthart.newstestapp.db"
    }
}