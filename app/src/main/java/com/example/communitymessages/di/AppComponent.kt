package com.example.communitymessages.di

import com.example.communitymessages.presentation.ui.details.MessageDetailsFragment
import com.example.communitymessages.presentation.ui.home.HomeFragment
import dagger.Component

@Component(
    modules = [
        NetworkModule::class
    ]
)
interface AppComponent {
    fun injectTimeline(fragment: HomeFragment)
    fun injectMessage(fragment: MessageDetailsFragment)
}