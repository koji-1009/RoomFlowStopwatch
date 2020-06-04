package com.dr1009.app.android.roomflowstopwatch.repository

import com.dr1009.app.android.roomflowstopwatch.db.MeasurementDao
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementEntity
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementState
import com.dr1009.app.android.roomflowstopwatch.secUntil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest
import java.time.OffsetDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchRepository @Inject constructor(private val dao: MeasurementDao) {

    val entity = dao.findFlow().map {
        it ?: MeasurementEntity.INIT_OBJECT
    }

    private val clock = entity.transformLatest {
        emit(OffsetDateTime.now())
        when (it.state) {
            MeasurementState.STOPWATCH_RUN -> {
                while (true) {
                    delay(INTERVAL_MILLI)
                    emit(OffsetDateTime.now())
                }
            }
            else -> {
                // nop
            }
        }
    }

    val elapsedTime = combine(entity, clock) { entity, event ->
        when (entity.state) {
            MeasurementState.INIT -> {
                0L
            }
            MeasurementState.STOPWATCH_RUN -> {
                entity.secUntil(event) + entity.elapsedSec
            }
            MeasurementState.STOPWATCH_STOP -> {
                entity.elapsedSec
            }
        }
    }

    // region stopwatch
    suspend fun startStopwatch() {
        val entity = dao.find() ?: MeasurementEntity.INIT_OBJECT
        val newEntity = entity.copy(
            state = MeasurementState.STOPWATCH_RUN,
            startDateTime = OffsetDateTime.now().toString()
        )
        dao.insert(newEntity)
    }

    suspend fun stopStopwatch() {
        val entity = dao.find() ?: MeasurementEntity.INIT_OBJECT
        val newEntity = entity.copy(
            state = MeasurementState.STOPWATCH_STOP,
            elapsedSec = entity.secUntil(OffsetDateTime.now()) + entity.elapsedSec
        )
        dao.insert(newEntity)
    }

    suspend fun resetStopwatch() {
        val entity = dao.find() ?: MeasurementEntity.INIT_OBJECT
        val newEntity = entity.copy(
            state = MeasurementState.STOPWATCH_STOP,
            startDateTime = OffsetDateTime.now().toString(),
            elapsedSec = 0L
        )
        dao.insert(newEntity)
    }

    suspend fun finishStopwatch(): Long {
        val entity = dao.find() ?: MeasurementEntity.INIT_OBJECT
        dao.insert(MeasurementEntity.INIT_OBJECT)

        return when (entity.state) {
            MeasurementState.STOPWATCH_RUN -> {
                entity.secUntil(OffsetDateTime.now()) + entity.elapsedSec
            }
            MeasurementState.STOPWATCH_STOP -> {
                entity.elapsedSec
            }
            else -> {
                0L
            }
        }
    }
    // endregion

    companion object {
        private const val INTERVAL_MILLI = 500L
    }

}
