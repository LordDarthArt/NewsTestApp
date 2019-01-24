package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News {
    @SerializedName("headlines")
    @Expose
    internal lateinit var item: List<Item>
}