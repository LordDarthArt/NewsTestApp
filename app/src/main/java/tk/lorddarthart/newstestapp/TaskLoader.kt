package tk.lorddarthart.newstestapp

import android.os.AsyncTask

class TaskLoader private constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        private var task: AsyncTask<*, *, *>? = null

        fun setTask(task: AsyncTask<*, *, *>) {
            TaskLoader.task = task
        }

        fun cancel() {
            TaskLoader.task!!.cancel(true)
        }
    }
}
