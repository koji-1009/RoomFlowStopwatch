package com.dr1009.app.android.roomflowstopwatch.ui

import com.dr1009.app.android.roomflowstopwatch.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}