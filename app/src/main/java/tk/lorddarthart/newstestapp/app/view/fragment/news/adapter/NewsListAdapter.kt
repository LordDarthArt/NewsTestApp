package tk.lorddarthart.newstestapp.app.view.fragment.news.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import tk.lorddarthart.newstestapp.R
import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import tk.lorddarthart.newstestapp.app.view.fragment.news.viewholder.NewsViewHolder
import tk.lorddarthart.newstestapp.utils.helper.NewsCheckListener
import java.text.SimpleDateFormat


@Suppress("DEPRECATION")
class NewsListAdapter(
    private val mainActivity: MainActivity,
    private val listItems: List<NewsEntity>
) : RecyclerView.Adapter<NewsViewHolder>() {
    private lateinit var view: View
    private lateinit var viewHolder: NewsViewHolder
    var selectedPosition: Int? = null
    private lateinit var item: NewsEntity

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
        view = LayoutInflater.from(mainActivity).inflate(R.layout.single_item, p0, false)
        viewHolder = NewsViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            item = listItems[position]
            holder.title.text = item.newsTitle
            holder.description.text = item.newsDesc
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
            holder.date.text = "Добавлено: " + sdf.format(item.newsDate * 1000)
            holder.rubric.text = "Рубрика: " + item.newsRubric
            val animationDrawable = mainActivity.getDrawable(R.drawable.loading)
            Glide.with(mainActivity)
                .load(item.newsPic)
                .placeholder(animationDrawable)
                .error(R.drawable.no_image_available)
                .into(holder.imageView)
            if (item.newsPicDesc != "") {
                holder.picDesc.text = Html.fromHtml(item.newsPicDesc).toString()
            } else {
                holder.picDeskBackground.visibility = View.GONE
            }
            // В некоторых случаях это предотвратит нежелательные ситуации
            holder.checkBox.setOnCheckedChangeListener(null)

            //Если положительно, чекнется, иначе нет
            with(holder.checkBox) {
                tag = position
                isChecked = position == selectedPosition
                setOnCheckedChangeListener(
                    NewsCheckListener(
                        this@NewsListAdapter,
                        holder.checkBox,
                        mainActivity,
                        position,
                        listItems[position].newsTitle,
                        listItems[position].newsDesc
                    )
                )
            }
    }

    fun getCheckedItems(): NewsEntity {
        return listItems[selectedPosition!!]
    }
}