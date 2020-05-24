package tk.lorddarthart.newstestapp.utils.helper

import tk.lorddarthart.newstestapp.app.model.entity.NewsEntity
import tk.lorddarthart.newstestapp.app.model.response.Item

object CompareObjects : Comparator<Item> {
    override fun compare(firstItem: Item, secondItem: Item): Int = when {
        firstItem.info.modified!! > secondItem.info.modified!! -> 1
        firstItem.info.modified!! < secondItem.info.modified!! -> -1
        else -> 0
    }
}