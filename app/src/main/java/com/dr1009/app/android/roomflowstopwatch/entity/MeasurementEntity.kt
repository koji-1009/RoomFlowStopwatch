package com.dr1009.app.android.roomflowstopwatch.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement")
data class MeasurementEntity(
    @PrimaryKey
    @ColumnInfo(name = "entity_id")
    val entityId: Int = 0,
    val state: MeasurementState,
    @ColumnInfo(name = "start_date_time")
    val startDateTime: String, // 2020-04-20'T'08:20:00+09:00
    @ColumnInfo(name = "elapsed_sec")
    val elapsedSec: Long = 0L
) {
    companion object {
        val INIT_OBJECT = MeasurementEntity(
            state = MeasurementState.INIT,
            startDateTime = ""
        )
    }
}

enum class MeasurementState {
    INIT,
    STOPWATCH_RUN,
    STOPWATCH_STOP
}
