package com.dr1009.app.android.roomflowstopwatch.di

import android.content.Context
import androidx.room.Room
import com.dr1009.app.android.roomflowstopwatch.db.AppDatabase
import com.dr1009.app.android.roomflowstopwatch.db.MeasurementDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    private const val DB_NAME = "room.db"

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMeasurementDao(database: AppDatabase): MeasurementDao = database.measurementDao()
}