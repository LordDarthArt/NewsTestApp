package tk.lorddarthart.newstestapp.app.view.fragment.news.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import tk.lorddarthart.newstestapp.R

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.findViewById(R.id.tvTitle)
    var description: TextView = itemView.findViewById(R.id.tvDesc)
    var date: TextView = itemView.findViewById(R.id.tvDate)
    var rubric: TextView = itemView.findViewById(R.id.tvRubric)
    var imageView: ImageView = itemView.findViewById(R.id.ivPicc)
    var picDesc: TextView = itemView.findViewById(R.id.tvPicDesc)
    var picDeskBackground: ConstraintLayout = itemView.findViewById(R.id.darkenBG)
    var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
}