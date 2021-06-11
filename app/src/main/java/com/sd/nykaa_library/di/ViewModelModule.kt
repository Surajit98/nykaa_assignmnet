package com.sd.nykaa_library.di

import com.sd.nykaa_library.ui.history.HistoryViewModel
import com.sd.nykaa_library.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {


    viewModel { MainViewModel(get()) }
    viewModel { HistoryViewModel(get()) }


}