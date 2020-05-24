package tk.lorddarthart.newstestapp.app.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.utils.static_collections.Strings

@Dao
interface NewsDao {
    @Query("SELECT * FROM ${Strings.NEWS_TABLE_NAME}")
    fun getNews(): MutableList<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewFeed(newFeed: NewsEntity): Long
}