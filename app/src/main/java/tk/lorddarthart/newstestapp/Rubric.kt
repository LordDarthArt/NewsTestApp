package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rubric {
    @SerializedName("slug")
    @Expose
    internal lateinit var slug: String
    @SerializedName("title")
    @Expose
    internal lateinit var title: String
}