package com.github.yeeun_yun97.toy.mobymovie.ui

import android.view.LayoutInflater
import com.github.yeeun_yun97.clone.ynmodule.ui.activity.YnBaseActivity
import com.github.yeeun_yun97.toy.mobymovie.databinding.ActivityMainBinding

class MainActivity : YnBaseActivity<ActivityMainBinding>() {
    override fun viewBindingInflate(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

}