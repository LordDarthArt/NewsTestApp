package tk.lorddarthart.newstestapp.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Info {
    @SerializedName("id")
    @Expose
    val id: String? = null
    @SerializedName("title")
    @Expose
    val title: String? = null
    @SerializedName("rightcol")
    @Expose
    val rightcol: String? = null
    @SerializedName("modified")
    @Expose
    val modified: Long? = null
}