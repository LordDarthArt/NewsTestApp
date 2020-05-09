package tk.lorddarthart.newstestapp.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {
    @SerializedName("self")
    @Expose
    internal lateinit var self: String
    @SerializedName("public")
    @Expose
    internal lateinit var public: String
}