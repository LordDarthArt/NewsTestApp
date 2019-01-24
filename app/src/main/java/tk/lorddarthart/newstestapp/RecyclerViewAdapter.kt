package tk.lorddarthart.newstestapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.net.URL
import java.util.concurrent.ExecutionException

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    internal lateinit var context: Context
    internal lateinit var listItems: List<Item>
    internal lateinit var view: View
    internal lateinit var viewHolder: ViewHolder
    internal lateinit var onItemTouchListener: OnItemTouchListener

    init {
        this.context = context
        this.listItems = listItems
        this.onItemTouchListener = onItemTouchListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item, p0, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

    override fun onBindViewHolder(p0: RecyclerViewAdapter.ViewHolder, p1: Int) {
        val item = listItems[p1]
        p0.tvTitle.text = item.info[0].title
        p0.tvDesc.text = item.info[0].rightcol
        try {
            UploadImageToItem(item.title_image[0].url, p0).execute().get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvTitle: TextView
        var tvDesc: TextView
        var ivNewsPic: ImageView

        init {

            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDesc = itemView.findViewById(R.id.tvDesc)
            ivNewsPic = itemView.findViewById(R.id.ivPic)

            itemView.setOnClickListener { v -> onItemTouchListener?.onCardViewTap(v, layoutPosition) }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class UploadImageToItem internal constructor(
        internal var urlString: String, internal var holder: ViewHolder
    ) : AsyncTask<Void, Void, Void>() {

        // Загрузка предоставленного к новости изображения отдельно от основного потока

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            try {
                val url = URL(urlString)
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                holder.ivNewsPic.setImageBitmap(bmp)
            } catch (e: Exception) {
                holder.ivNewsPic.setImageDrawable(context.resources.getDrawable(R.drawable.no_image_available))
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)
        }
    }

}