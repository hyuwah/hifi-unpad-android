package unpad.fmipa.hifi.android.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import unpad.fmipa.hifi.android.presentation.home.EventCalendarViewModel

val viewModelModule = module {

    viewModel { EventCalendarViewModel(get()) }

}