package tk.lorddarthart.newstestapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("type")
    @Expose
    internal lateinit var type : String
    @SerializedName("info")
    @Expose
    internal lateinit var info : List<Info>
    @SerializedName("links")
    @Expose
    internal lateinit var links : List<Links>
    @SerializedName("rubric")
    @Expose
    internal lateinit var rubric : List<Rubric>
    @SerializedName("tags")
    @Expose
    internal lateinit var tags : List<Tags>
    @SerializedName("title_image")
    @Expose
    internal lateinit var title_image : List<TitlePic>
}