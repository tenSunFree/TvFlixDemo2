package com.home.tvflixdemo2.common.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.home.tvflixdemo2.common.db.dao.TFDDao
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity

/**
 * entities: 表名
 * version: 資料庫版本, 每當我們改變資料庫中的內容時它都會增加
 * 如果不添加 exportSchema = false 會報警告
 */
@Database(entities = [FavoritePhotoEntity::class], version = 1, exportSchema = false)
abstract class TFDDatabase : RoomDatabase() {

    abstract fun tFDDao(): TFDDao

    companion object {
        const val DATABASE_NAME = "tfd.db"
    }
}
