package com.home.tvflixdemo2.common.db.module

import android.content.Context
import androidx.room.Room
import com.home.tvflixdemo2.common.db.dao.TFDDao
import com.home.tvflixdemo2.common.db.database.TFDDatabase
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
object TFDDatabaseModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideTFDDatabase(context: Context): TFDDatabase {
        return Room.databaseBuilder(context, TFDDatabase::class.java, TFDDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideTFDDao(database: TFDDatabase): TFDDao {
        return database.tFDDao()
    }
}
