package tk.lorddarthart.newstestapp.app.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tk.lorddarthart.newstestapp.utils.static_collections.Strings

@Entity(tableName = Strings.NEWS_TABLE_NAME)
data class NewsEntity(
    @PrimaryKey @ColumnInfo(name = "news_title") val newsTitle: String,
    @ColumnInfo(name = "news_rubric") val newsRubric: String,
    @ColumnInfo(name = "news_desc") val newsDesc: String,
    @ColumnInfo(name = "news_pic") val newsPic: String,
    @ColumnInfo(name = "news_picdesc") val newsPicDesc: String,
    @ColumnInfo(name = "news_date") val newsDate: Long
)