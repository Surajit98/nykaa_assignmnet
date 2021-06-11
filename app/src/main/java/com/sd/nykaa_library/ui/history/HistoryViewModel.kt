package com.sd.nykaa_library.ui.history

import com.sd.nykaa_library.data.repository.SessionRepository
import com.sd.nykaa_library.ui.base.BaseViewModel

class HistoryViewModel(private val repository: SessionRepository) : BaseViewModel() {


    fun getAllSessions() = repository.getAllSession()

}