package tk.lorddarthart.newstestapp.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Item {
    @SerializedName("type")
    @Expose
    internal var type : String = ""
    @SerializedName("info")
    @Expose
    internal var info : Info =
        Info()
    @SerializedName("links")
    @Expose
    internal var links : Links =
        Links()
    @SerializedName("rubric")
    @Expose
    internal var rubric : Rubric? =
        Rubric()
    @SerializedName("tags")
    @Expose
    internal var tags : List<Tags> = LinkedList()
    @SerializedName("title_image")
    @Expose
    internal var title_image : TitlePic? =
        TitlePic()
}