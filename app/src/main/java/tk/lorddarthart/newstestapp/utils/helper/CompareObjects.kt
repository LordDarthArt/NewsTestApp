package tk.lorddarthart.newstestapp.utils.helper

import tk.lorddarthart.newstestapp.app.model.response.Item

object CompareObjects: Comparator<Item> {
    override fun compare(a: Item, b: Item): Int = when {
        a.info.modified != b.info.modified -> (b.info.modified!! - a.info.modified!!).toInt()
        else -> (a.info.modified!! - b.info.modified!!).toInt()
    }
}