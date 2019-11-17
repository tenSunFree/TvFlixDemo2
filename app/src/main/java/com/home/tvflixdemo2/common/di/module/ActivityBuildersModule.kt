package com.home.tvflixdemo2.common.di.module

import com.home.tvflixdemo2.favoritelist.view.activity.FavoriteListActivity
import com.home.tvflixdemo2.list.view.activity.ListActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): ListActivity

    @ContributesAndroidInjector
    abstract fun bindFavoriteShowsActivity(): FavoriteListActivity
}
