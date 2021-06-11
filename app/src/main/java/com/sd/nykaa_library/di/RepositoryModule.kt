package com.sd.nykaa_library.di

import com.sd.nykaa_library.data.database.dao.SessionDao
import com.sd.nykaa_library.data.repository.SessionRepository
import org.koin.dsl.module

var repositoryModule = module {


    fun provideSessionRepository(sessionDao: SessionDao) =
        SessionRepository(
            sessionDao
        )


    single { provideSessionRepository(get()) }
}