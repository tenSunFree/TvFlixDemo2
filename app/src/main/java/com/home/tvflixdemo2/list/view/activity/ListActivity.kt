package com.home.tvflixdemo2.list.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.home.tvflixdemo2.R
import com.home.tvflixdemo2.common.base.BaseActivity
import com.home.tvflixdemo2.favoritelist.view.activity.FavoriteListActivity
import com.home.tvflixdemo2.list.model.viewdata.ListViewData
import com.home.tvflixdemo2.list.model.viewstate.HomeViewState
import com.home.tvflixdemo2.list.model.viewstate.Loading
import com.home.tvflixdemo2.list.model.viewstate.NetworkError
import com.home.tvflixdemo2.list.model.viewstate.Success
import com.home.tvflixdemo2.list.view.adapter.ListAdapter
import com.home.tvflixdemo2.list.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : BaseActivity(), ListAdapter.Callback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initializeViewModel()
        initializeView()
    }

    private fun initializeViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
        homeViewModel.requestData()
        homeViewModel.getHomeViewState().observe(this, Observer { setViewState(it) })
    }

    private fun initializeView() {
        image_view_favorite.setOnClickListener { FavoriteListActivity.start(this) }
    }

    private fun setViewState(homeViewState: HomeViewState) {
        when (homeViewState) {
            is Loading -> setProgress(true)
            is NetworkError -> {
                setProgress(false)
                showError(homeViewState.message!!)
            }
            is Success -> {
                setProgress(false)
                showRecyclerView(homeViewState.data)
            }
        }
    }

    private fun setProgress(isLoading: Boolean) {
        if (isLoading) showProgress()
        else hideProgress()
    }

    private fun showRecyclerView(data: ListViewData) {
        val adapter = ListAdapter(this)
        adapter.updateList(data.favoritePhotoBeanList.toMutableList())
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onFavoriteClicked(favoritePhotoBean: ListViewData.FavoritePhotoBean) {
        if (!favoritePhotoBean.isFavoriteShow) homeViewModel.addToFavorite(favoritePhotoBean)
        else homeViewModel.removeFromFavorite(favoritePhotoBean)
    }
}
