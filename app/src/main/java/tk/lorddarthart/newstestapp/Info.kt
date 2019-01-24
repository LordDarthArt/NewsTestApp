package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Info {
    @SerializedName("id")
    @Expose
    internal lateinit var id: String
    @SerializedName("title")
    @Expose
    internal lateinit var title: String
    @SerializedName("rightcol")
    @Expose
    internal lateinit var rightcol: String
    @SerializedName("modified")
    @Expose
    internal var modified: Long? = null
}