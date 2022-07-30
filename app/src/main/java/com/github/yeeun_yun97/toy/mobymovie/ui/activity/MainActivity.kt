package com.github.yeeun_yun97.toy.mobymovie.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.net.*
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.github.yeeun_yun97.clone.ynmodule.ui.activity.YnBaseActivity
import com.github.yeeun_yun97.clone.ynmodule.ui.component.YnConfirmBaseDialogFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.ActivityMainBinding

class MainActivity : YnBaseActivity<ActivityMainBinding>() {
    private lateinit var connManager: ConnectivityManager
    private var confirmDialog: YnConfirmBaseDialogFragment? = null

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            showNetworkNotConnectedDialog()
        }
    }

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = NetworkRequest.Builder()
        connManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connManager.registerNetworkCallback(builder.build(), networkCallBack)
    }

    override fun onResume() {
        super.onResume()
        val connected = connManager.activeNetworkInfo?.isConnected == true
        if (!connected) showNetworkNotConnectedDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        connManager.unregisterNetworkCallback(networkCallBack)
    }

    private fun showNetworkNotConnectedDialog() {
        val dialog = YnConfirmBaseDialogFragment(
            getString(R.string.connectionFailTitle),
            getString(R.string.connectionFailMessage),
            ::closeApp
        )
        dialog.isCancelable=false
        dialog.show(supportFragmentManager, getString(R.string.connectionFailTag))
        this.confirmDialog= dialog
    }

    private fun closeApp() {
        this.confirmDialog?.dismiss()
        this.confirmDialog = null
        finishAffinity()
    }


}