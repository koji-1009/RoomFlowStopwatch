package com.dr1009.app.android.roomflowstopwatch

import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementEntity
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

fun MeasurementEntity.secUntil(until: OffsetDateTime) =
    OffsetDateTime.parse(startDateTime).until(until, ChronoUnit.SECONDS)

fun formatMeasureTime(duration: Long): String {
    val hour = duration / 3600L
    val minute = duration % 3600L / 60L
    val second = duration % 60L

    return String.format(Locale.US, "%02d:%02d:%02d", hour, minute, second)
}