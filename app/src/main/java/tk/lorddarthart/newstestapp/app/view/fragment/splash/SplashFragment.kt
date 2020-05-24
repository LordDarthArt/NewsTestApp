package tk.lorddarthart.newstestapp.app.view.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tk.lorddarthart.newstestapp.R
import tk.lorddarthart.newstestapp.app.presenter.fragment.splash.SplashFragmentPresenter
import tk.lorddarthart.newstestapp.app.view.base.BaseFragment
import tk.lorddarthart.newstestapp.app.view.fragment.news.NewsFragment
import tk.lorddarthart.newstestapp.databinding.SplashFragmentBinding
import tk.lorddarthart.newstestapp.utils.helper.fadeHide
import tk.lorddarthart.newstestapp.utils.helper.fadeShow
import tk.lorddarthart.newstestapp.utils.helper.setVisible

class SplashFragment: BaseFragment(), SplashView {
    @InjectPresenter
    lateinit var splashPresenter: SplashFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialization()
    }

    override fun setupVariables() {

    }

    override fun initListeners() {
        with (fragmentBinding as SplashFragmentBinding) {
            splashRetry.setOnClickListener {
                splashRetry.fadeHide()
                initialization()
            }
        }
    }

    override fun start() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            if (!activity.netReceiver?.checkInternet()!!) {
                (fragmentBinding as SplashFragmentBinding).splashRetry.fadeShow()
            } else {
                activity.supportFragmentManager.beginTransaction().replace(R.id.container_base, NewsFragment()).commitAllowingStateLoss()
            }
        }
    }
}