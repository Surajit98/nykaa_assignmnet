package com.sd.nykaa_library.data.repository

import androidx.lifecycle.MutableLiveData
import com.sd.nykaa_library.data.database.dao.SessionDao
import com.sd.nykaa_library.data.database.entity.Sessions

class SessionRepository(
    private val sessionDao: SessionDao
) {

    private val textToShow = MutableLiveData<String>()

    fun insertSession(session: Sessions) = sessionDao.insertSession(session)

    fun getText() = textToShow

    fun updateSession(session: Sessions) {
        sessionDao.updateSession(session)
    }


    fun getAllSession() = sessionDao.getSessions()

    fun getActiveSession() = sessionDao.getActiveSession()


}