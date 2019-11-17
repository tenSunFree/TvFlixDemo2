package com.home.tvflixdemo2.favoritelist.view.adapter

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
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity
import com.home.tvflixdemo2.databinding.ActivityListRecyclerViewItemBinding

class FavoriteListAdapter(
    private val favoritePhotoEntityList: MutableList<FavoritePhotoEntity>
) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ActivityListRecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        val holder = FavoriteListHolder(binding)
        holder.binding.showFavoriteIcon = false
        return holder
    }

    override fun onBindViewHolder(holder: FavoriteListHolder, position: Int) {
        val entity = favoritePhotoEntityList[position]
        configureTitle(holder.binding.textViewTitle, entity)
        configureImage(holder.binding.imageViewThumbnail, entity)
        configureFavoriteIcon(holder.binding.imageViewFavorite, entity)
    }

    override fun getItemCount(): Int {
        return favoritePhotoEntityList.size
    }

    private fun configureTitle(textView: TextView, entity: FavoritePhotoEntity) {
        textView.text = entity.title
    }

    private fun configureImage(showImage: ImageView, entity: FavoritePhotoEntity) {
        Glide.with(showImage.context).load(entity.thumbnailUrl)
            .apply(RequestOptions.placeholderOf(R.color.grey))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(showImage)
    }

    private fun configureFavoriteIcon(favoriteIcon: ImageView, entity: FavoritePhotoEntity) {
        if (entity.isFavorite) {
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

    class FavoriteListHolder(val binding: ActivityListRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
