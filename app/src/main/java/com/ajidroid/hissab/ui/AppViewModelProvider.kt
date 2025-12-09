package com.ajidroid.hissab.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ajidroid.hissab.HissabApplication
import com.ajidroid.hissab.ui.home.HomeScreenViewModel

object viewModelProvider{
    val Factory = viewModelFactory{

        initializer {
            HomeScreenViewModel(HissabApplication().container.membersRepository)
        }

//        initializer {
//            MemberEntryViewModel(HissabApplication().container.membersRepository)
//        }
//
//        initializer {
//            MemberDetailViewModel(
//                this.createSavedStateHandle(),
//                HissabApplication().container.membersRepository
//            )
//        }
    }
}

fun CreationExtras.hissabApplication(): HissabApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HissabApplication)