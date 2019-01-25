package tk.lorddarthart.newstestapp

import android.support.design.widget.Snackbar
import android.view.View

class SnackBarNotifications {

    fun noInternerConstraintLayout(view: View) {
        val myAwesomeSnackbar = Snackbar.make(
            view,
            "Сеть недоступна",
            Snackbar.LENGTH_LONG
        )
        myAwesomeSnackbar.show()
    }

    fun onInternetRestoreConstraintLayout(view: View) {
        val myAwesomeSnackbar = Snackbar.make(
            view,
            "Доступ к сети восстановлен",
            Snackbar.LENGTH_LONG
        )
        myAwesomeSnackbar.show()
    }
}
