package com.angelinaandronova.myimagedownloadapplication.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.angelinaandronova.myimagedownloadapplication.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

     @Binds
     @IntoMap
     @ViewModelKey(MainViewModel::class)
     internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}