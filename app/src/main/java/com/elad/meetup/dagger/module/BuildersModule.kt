package com.elad.meetup.dagger.module

import com.elad.meetup.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCryptocurrenciesActivity(): MainActivity
}
