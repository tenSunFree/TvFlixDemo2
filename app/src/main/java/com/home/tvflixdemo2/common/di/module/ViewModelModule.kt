package com.home.tvflixdemo2.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.tvflixdemo2.common.di.TFDViewModelFactory
import com.home.tvflixdemo2.common.di.ViewModelKey
import com.home.tvflixdemo2.favoritelist.viewmodel.FavoriteListViewModel
import com.home.tvflixdemo2.list.viewmodel.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteListViewModel::class)
    abstract fun bindFavoriteShowsViewModel(favoriteShowsViewModel: FavoriteListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TFDViewModelFactory): ViewModelProvider.Factory
}
