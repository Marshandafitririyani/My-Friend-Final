package com.marshanda.myfriendapi.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.marshanda.myfriendapi.R


class ViewModelHelper {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        fun loadImageRecipe(view: ImageView, imageUrl: String?) {

            view.setImageDrawable(null)

            imageUrl?.let {
                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.img_no_image)
                    .error(R.drawable.img_errornotfound)
                    .into(view)

            }

        }

    }

}