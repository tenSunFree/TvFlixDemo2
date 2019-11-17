package com.home.tvflixdemo2.common.di.component

import android.app.Application
import com.home.tvflixdemo2.common.TFDApplication
import com.home.tvflixdemo2.common.db.module.TFDDatabaseModule
import com.home.tvflixdemo2.common.di.module.ActivityBuildersModule
import com.home.tvflixdemo2.common.di.module.AppModule
import com.home.tvflixdemo2.common.network.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class,
        ActivityBuildersModule::class, NetworkModule::class, TFDDatabaseModule::class]
)
interface ApplicationComponent {

    fun inject(tFDApplication: TFDApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
