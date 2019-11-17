package com.home.tvflixdemo2.favoritelist.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.home.tvflixdemo2.R
import com.home.tvflixdemo2.common.base.BaseActivity
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity
import com.home.tvflixdemo2.favoritelist.model.viewstate.FavoriteListViewState
import com.home.tvflixdemo2.favoritelist.model.viewstate.Loading
import com.home.tvflixdemo2.favoritelist.model.viewstate.NetworkError
import com.home.tvflixdemo2.favoritelist.model.viewstate.Success
import com.home.tvflixdemo2.favoritelist.view.adapter.FavoriteListAdapter
import com.home.tvflixdemo2.favoritelist.viewmodel.FavoriteListViewModel
import kotlinx.android.synthetic.main.activity_favorite_list.*
import javax.inject.Inject

class FavoriteListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var favoriteShowsViewModel: FavoriteListViewModel

    companion object {
        private const val FAVORITE_ICON_START_OFFSET = 13
        private const val FAVORITE_ICON_END_OFFSET = 14
        fun start(context: Activity) {
            val starter = Intent(context, FavoriteListActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        favoriteShowsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FavoriteListViewModel::class.java)
        favoriteShowsViewModel.loadFavoritePhotoEntityList()
        favoriteShowsViewModel.getFavoriteListViewStateLiveData()
            .observe(this, Observer { setViewState(it) })
    }

    private fun setViewState(favoriteListViewState: FavoriteListViewState) {
        when (favoriteListViewState) {
            is Loading -> {
                setProgress(true)
            }
            is NetworkError -> {
                setProgress(false)
                showError(favoriteListViewState.message!!)
            }
            is Success -> {
                setProgress(false)
                showRecyclerView(favoriteListViewState.data.favoritePhotoEntityList)
            }
        }
    }

    private fun setProgress(isLoading: Boolean) {
        if (isLoading) showProgress()
        else hideProgress()
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showRecyclerView(favoritePhotoEntityList: List<FavoritePhotoEntity>) {
        progress.visibility = View.GONE
        if (favoritePhotoEntityList.isNotEmpty()) {
            recycler_view.visibility = View.VISIBLE
            text_view_favorite_hint.visibility = View.GONE
            val adapter = FavoriteListAdapter(favoritePhotoEntityList.toMutableList())
            recycler_view.setHasFixedSize(true)
            recycler_view.adapter = adapter
        } else {
            recycler_view.visibility = View.GONE
            text_view_favorite_hint.visibility = View.VISIBLE
            val bookmarkSpan =
                ImageSpan(this, R.drawable.icon_activity_list_recycler_view_item_not_favorite)
            val spannableString = SpannableString(getString(R.string.favorite_hint_msg))
            spannableString.setSpan(
                bookmarkSpan,
                FAVORITE_ICON_START_OFFSET,
                FAVORITE_ICON_END_OFFSET, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text_view_favorite_hint.text = spannableString
        }
    }
}
