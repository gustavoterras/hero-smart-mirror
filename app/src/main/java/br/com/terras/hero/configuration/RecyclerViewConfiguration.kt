package br.com.terras.hero.configuration

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v7.widget.RecyclerView
import br.com.terras.hero.BR
import br.com.terras.hero.adapter.RecyclerBindingAdapter

/**
 * Das!
 *
 * Created by Gustavo Terras on 14/03/2018.
 * Copyright © 2017 Woop. All rights reserved.
 */

class RecyclerViewConfiguration : BaseObservable() {

    @get:Bindable
    var layoutManager: RecyclerView.LayoutManager? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.layoutManager)
        }

    @get:Bindable
    var itemAnimator: RecyclerView.ItemAnimator? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemAnimator)
        }

    @get:Bindable
    var adapter: RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.adapter)
        }
}