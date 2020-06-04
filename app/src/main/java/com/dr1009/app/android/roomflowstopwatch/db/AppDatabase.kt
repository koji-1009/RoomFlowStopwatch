package com.dr1009.app.android.roomflowstopwatch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementEntity

@Database(
    entities = [MeasurementEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}