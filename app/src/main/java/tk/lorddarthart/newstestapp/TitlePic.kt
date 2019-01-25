package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TitlePic {
    @SerializedName("credits")
    @Expose
    internal var credits: String = ""
    @SerializedName("url")
    @Expose
    internal var url: String = ""
}