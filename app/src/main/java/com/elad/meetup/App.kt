package com.elad.meetup

import android.app.Activity
import android.app.Application
import com.elad.meetup.dagger.component.DaggerAppComponent
import com.elad.meetup.dagger.module.AppModule
import com.elad.meetup.dagger.module.NetModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(BuildConfig.URL))
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
