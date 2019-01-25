package tk.lorddarthart.newstestapp

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.koushikdutta.ion.Ion
import java.text.SimpleDateFormat

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItems: List<Item>
    private lateinit var view: View
    private lateinit var viewHolder: ViewHolder

    constructor(context: Context, listItems: List<Item>) : this() {
        this.context = context
        this.listItems = listItems
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item, p0, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(p0: RecyclerViewAdapter.ViewHolder, p1: Int) {
        val item = listItems[p1]
        p0.tvTitle.text = item.info.title
        p0.tvDesc.text = item.info.rightcol
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
        p0.tvDate.text = "Добавлено: "+sdf.format(item.info.modified!! *1000)
        p0.tvRubric.text = "Рубрика: "+item.rubric.title
        Ion.with(context)
            .load(item.title_image!!.url)
            .withBitmap()
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image_available)
            .intoImageView(p0.ivNewsPic)
        if (item.title_image!!.credits != "") {
            p0.tvPicDesc.text = item.title_image!!.credits
        } else {
            p0.constraintLayout.visibility = View.GONE
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvRubric: TextView = itemView.findViewById(R.id.tvRubric)
        var ivNewsPic: ImageView = itemView.findViewById(R.id.ivPicc)
        var tvPicDesc: TextView = itemView.findViewById(R.id.tvPicDesc)
        var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.darkenBG)

    }
}