package com.home.tvflixdemo2.list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.home.tvflixdemo2.R
import com.home.tvflixdemo2.databinding.ActivityListRecyclerViewItemBinding
import com.home.tvflixdemo2.list.model.bean.PhotoBean
import com.home.tvflixdemo2.list.model.viewdata.ListViewData

class ListAdapter constructor(
    private val callback: Callback
) : RecyclerView.Adapter<ListAdapter.ListAdapterHolder>() {

    interface Callback {
        fun onFavoriteClicked(favoritePhotoBean: ListViewData.FavoritePhotoBean)
    }

    private var favoritePhotoBeanList: MutableList<ListViewData.FavoritePhotoBean> =
        mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ActivityListRecyclerViewItemBinding
            .inflate(layoutInflater, parent, false)
        val showHolder = ListAdapterHolder(binding)
        showHolder.binding.showFavoriteIcon = true
        showHolder.binding.imageViewFavorite.setOnClickListener {
            onFavouriteIconClicked(showHolder.adapterPosition)
        }
        return showHolder
    }

    override fun onBindViewHolder(holder: ListAdapterHolder, position: Int) {
        val favoritePhotoBean = favoritePhotoBeanList[position]
        configureTitle(holder.binding.textViewTitle, favoritePhotoBean.photoBean)
        configureImage(holder.binding.imageViewThumbnail, favoritePhotoBean.photoBean)
        configureFavoriteIcon(holder.binding.imageViewFavorite, favoritePhotoBean.isFavoriteShow)
    }

    override fun getItemCount(): Int {
        return favoritePhotoBeanList.size
    }

    fun updateList(list: MutableList<ListViewData.FavoritePhotoBean>) {
        favoritePhotoBeanList = list
    }

    private fun onFavouriteIconClicked(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val favoritePhotoBean = favoritePhotoBeanList[position]
            val isFavorite = favoritePhotoBean.isFavoriteShow
            val updatedShowViewData =
                favoritePhotoBean.copy(isFavoriteShow = !isFavorite)
            favoritePhotoBeanList[position] = updatedShowViewData
            notifyItemChanged(position)
            callback.onFavoriteClicked(favoritePhotoBean)
        }
    }

    private fun configureTitle(textView: TextView, bean: PhotoBean) {
        textView.text = bean.title
    }

    private fun configureImage(showImage: ImageView, bean: PhotoBean) {
        Glide.with(showImage.context).load(bean.thumbnailUrl)
            .apply(RequestOptions.placeholderOf(R.color.grey))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(showImage)
    }

    private fun configureFavoriteIcon(favoriteIcon: ImageView, favorite: Boolean) {
        if (favorite) {
            val favoriteDrawable = AppCompatResources
                .getDrawable(
                    favoriteIcon.context,
                    R.drawable.icon_activity_list_recycler_view_item_favorite
                )
            favoriteIcon.setImageDrawable(favoriteDrawable)
        } else {
            val unFavoriteDrawable = AppCompatResources
                .getDrawable(
                    favoriteIcon.context,
                    R.drawable.icon_activity_list_recycler_view_item_not_favorite
                )
            favoriteIcon.setImageDrawable(unFavoriteDrawable)
        }
    }

    class ListAdapterHolder(val binding: ActivityListRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
