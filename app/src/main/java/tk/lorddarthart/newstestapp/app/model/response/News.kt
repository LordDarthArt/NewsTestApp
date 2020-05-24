package tk.lorddarthart.newstestapp.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tk.lorddarthart.newstestapp.app.model.response.Item

class News {
    @SerializedName("headlines")
    @Expose
    val item: List<Item>? = null
}