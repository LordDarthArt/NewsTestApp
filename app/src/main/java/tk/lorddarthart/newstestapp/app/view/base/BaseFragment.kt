package tk.lorddarthart.newstestapp.app.view.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import tk.lorddarthart.newstestapp.utils.helper.Loggable
import tk.lorddarthart.newstestapp.utils.lib.MvpFragment

abstract class BaseFragment: MvpFragment(), Loggable {
    lateinit var fragmentBinding: ViewDataBinding
    protected lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    protected fun initialization() {
        setupVariables()
        initListeners()
        start()
    }

    abstract fun setupVariables()
    abstract fun initListeners()
    abstract fun start()
}