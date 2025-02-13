package com.ajidroid.hissab.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ajidroid.hissab.HissabApplication
import com.ajidroid.hissab.ui.home.HomeScreenViewModel
import com.ajidroid.hissab.ui.member.MemberDetailViewModel

object viewModelProvider{
    val Factory = viewModelFactory{

        initializer {
            HomeScreenViewModel(HissabApplication().container.membersRepository)
        }

        initializer {
            MemberDetailViewModel(
                this.createSavedStateHandle(),
                HissabApplication().container.membersRepository
            )
        }
    }
}