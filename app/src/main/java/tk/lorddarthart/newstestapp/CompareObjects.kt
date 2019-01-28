package tk.lorddarthart.newstestapp

class CompareObjects {

    companion object : Comparator<Item> {

        override fun compare(a: Item, b: Item): Int = when {
            a.info.modified != b.info.modified -> (b.info.modified!! - a.info.modified!!).toInt()
            else -> (a.info.modified!! - b.info.modified!!).toInt()
        }
    }
}