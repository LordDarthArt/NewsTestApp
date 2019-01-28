package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rubric {
    @SerializedName("slug")
    @Expose
    internal var slug: String = ""
    @SerializedName("title")
    @Expose
    internal var title: String = ""
}