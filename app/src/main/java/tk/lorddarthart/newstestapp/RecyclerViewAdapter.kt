package tk.lorddarthart.newstestapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import com.koushikdutta.ion.Ion
import java.text.SimpleDateFormat
import android.widget.CheckBox
import android.widget.TextView
import android.widget.CompoundButton

@Suppress("DEPRECATION")
class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItems: List<Item>
    private lateinit var view: View
    private lateinit var viewHolder: ViewHolder
    private lateinit var recyclerView: RecyclerView
    private var notificationManager: NotificationManager? = null
    private var selectedPosition: Int? = null
    private lateinit var item: Item

    constructor(context: Context, listItems: List<Item>, recyclerView: RecyclerView) : this() {
        this.context = context
        this.listItems = listItems
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item, p0, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        item = listItems[position]
        holder.tvTitle.text = item.info.title
        holder.tvDesc.text = item.info.rightcol
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
        holder.tvDate.text = "Добавлено: " + sdf.format(item.info.modified!! * 1000)
        holder.tvRubric.text = "Рубрика: " + item.rubric.title
        Ion.with(context)
            .load(item.title_image!!.url)
            .withBitmap()
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image_available)
            .intoImageView(holder.ivNewsPic)
        if (item.title_image!!.credits != "") {
            holder.tvPicDesc.text = Html.fromHtml(item.title_image!!.credits).toString()
        } else {
            holder.constraintLayout.visibility = View.GONE
        }
        // В некоторых случаях это предотвратит нежелательные ситуации
        holder.checkBox.setOnCheckedChangeListener(null)

        //Если положительно, чекнется, иначе нет
        holder.checkBox.tag = position
        holder.checkBox.isChecked = position == selectedPosition
        holder.checkBox.setOnCheckedChangeListener(CheckListener(holder.checkBox, position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvRubric: TextView = itemView.findViewById(R.id.tvRubric)
        var ivNewsPic: ImageView = itemView.findViewById(R.id.ivPicc)
        var tvPicDesc: TextView = itemView.findViewById(R.id.tvPicDesc)
        var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.darkenBG)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

    }

    internal inner class CheckListener(private val checkbox: CheckBox, position: Int) :
        CompoundButton.OnCheckedChangeListener {
        var data: Item? = null
        var position: Int? = null

        init {
            this.position = position
        }

        override fun onCheckedChanged(
            buttonView: CompoundButton,
            isChecked: Boolean
        ) {

            if (isChecked) {
                checkbox.isChecked = true
                selectedPosition = position
                this@RecyclerViewAdapter.notifyDataSetChanged()
                PushNotifications().getNotified(context, listItems[selectedPosition!!].info.title, listItems[selectedPosition!!].info.rightcol, selectedPosition!!)
            } else {
                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager!!.cancel(PushNotifications().uniqueId)
                checkbox.isChecked = false

            }
            buttonView.isChecked = isChecked

        }


    }

    fun getCheckedItems(): Item {
        return listItems[selectedPosition!!]
    }
}