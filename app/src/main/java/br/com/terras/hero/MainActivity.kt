package br.com.terras.hero

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import br.com.terras.hero.viewmodel.MainViewModel

/**
 * Hero Smart Mirror!
 *
 * Created by Gustavo Terras on 27/12/2018.
 * Copyright © 2018. All rights reserved.
 */

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)
                .setVariable(BR.viewModel, MainViewModel(this))
    }
}
