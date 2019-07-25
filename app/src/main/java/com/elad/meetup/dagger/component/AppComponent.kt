package com.elad.meetup.dagger.component

import com.elad.meetup.App
import com.elad.meetup.dagger.module.AppModule
import com.elad.meetup.dagger.module.BuildersModule
import com.elad.meetup.dagger.module.NetModule

import dagger.Component
import dagger.android.AndroidInjectionModule

import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class) , (BuildersModule::class), (AppModule::class), (NetModule::class)]
)
interface AppComponent {
    fun inject(app: App)
}
