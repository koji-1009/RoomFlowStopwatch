package com.dr1009.app.android.roomflowstopwatch.db

import androidx.room.TypeConverter
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementState

internal class Converters {

    @TypeConverter
    fun MeasurementState.toStr(): String = this.toString()

    @TypeConverter
    fun String?.toMeasurementState() =
        if (this.isNullOrEmpty()) {
            MeasurementState.INIT
        } else {
            MeasurementState.valueOf(this)
        }
}