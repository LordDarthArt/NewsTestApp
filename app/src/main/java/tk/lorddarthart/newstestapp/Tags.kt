package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tags {
    @SerializedName("title")
    @Expose
    internal lateinit var title: String
    @SerializedName("slug")
    @Expose
    internal lateinit var slug: String
}