package com.dr1009.app.android.roomflowstopwatch.di

import android.content.Context
import com.dr1009.app.android.roomflowstopwatch.App
import com.dr1009.app.android.roomflowstopwatch.ui.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}