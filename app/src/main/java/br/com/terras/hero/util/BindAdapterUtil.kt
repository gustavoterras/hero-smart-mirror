package br.com.terras.hero.util

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import br.com.terras.hero.configuration.RecyclerViewConfiguration
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

object BindAdapterUtil {

    @JvmStatic
    @BindingAdapter("srcUrl")
    fun loadImage(view: ImageView, url: String?) {

        val options: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

//        if (srcError != 0)
//            options.error(VectorDrawableCompat.create(view.getContext().getResources(), srcError, null));
//        if (srcPlaceHolder != 0)
//            options.placeholder(VectorDrawableCompat.create(view.getContext().getResources(), srcPlaceHolder, null));

        if (url == null) return

        Glide.with(view.context)
            .load(url)
            .apply(options)
            .transition(withCrossFade())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("recyclerView_configuration")
    fun configureRecyclerView(recyclerView: RecyclerView, recyclerViewConfiguration: RecyclerViewConfiguration) {
        recyclerView.layoutManager = recyclerViewConfiguration.layoutManager
        recyclerView.itemAnimator = recyclerViewConfiguration.itemAnimator
        recyclerView.adapter = recyclerViewConfiguration.adapter
    }
}