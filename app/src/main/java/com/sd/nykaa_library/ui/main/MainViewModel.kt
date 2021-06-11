package com.sd.nykaa_library.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sd.nykaa_library.data.database.entity.Sessions
import com.sd.nykaa_library.data.repository.SessionRepository
import com.sd.nykaa_library.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SessionRepository) : BaseViewModel() {


    val session = MutableLiveData<Sessions?>()
    val activeSession = MutableLiveData<Boolean>()
    val textToShow = MutableLiveData<String>()
    val toggleSession = MutableLiveData<Boolean>()

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch(Dispatchers.IO) {
            session.postValue(repository.getActiveSession())
        }

    }

     fun toggleSession() {
        toggleSession.value = !(activeSession.value ?: false)
    }

    fun getText()=repository.getText()


}