package com.sd.nykaa_library.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sd.nykaa_library.data.database.dao.SessionDao
import com.sd.nykaa_library.data.database.entity.Sessions

@Database(entities = [Sessions::class]
    , version = 1,
    exportSchema = false)
internal abstract class AppDatabase :RoomDatabase(){

    abstract fun getSessionDao():SessionDao

}