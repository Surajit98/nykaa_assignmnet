package com.sd.nykaa_library.ui.base

import androidx.lifecycle.ViewModel
import com.sd.nykaa_library.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val onBackPressed = SingleLiveEvent<Void>()

    fun back() {
        onBackPressed.call()
    }
}