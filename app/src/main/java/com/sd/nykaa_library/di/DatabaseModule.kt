package com.sd.nykaa_library.di

import android.content.Context
import androidx.room.Room
import com.sd.nykaa_library.constants.AppConstants
import com.sd.nykaa_library.data.database.AppDatabase
import com.sd.nykaa_library.data.database.dao.SessionDao
import org.koin.dsl.module

var dataBaseModule = module {


    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppConstants.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    fun provideSessionsDao(appDatabase: AppDatabase): SessionDao {
        return appDatabase.getSessionDao()
    }



    single { provideDatabase(get()) }
    single { provideSessionsDao(get()) }


}