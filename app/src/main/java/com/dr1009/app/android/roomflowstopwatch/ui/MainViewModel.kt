package com.dr1009.app.android.roomflowstopwatch.ui

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementState
import com.dr1009.app.android.roomflowstopwatch.formatMeasureTime
import com.dr1009.app.android.roomflowstopwatch.repository.StopwatchRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: StopwatchRepository) : ViewModel() {

    private val state = repository.entity.map { it.state }.asLiveData()
    val isRun = state.map { it == MeasurementState.STOPWATCH_RUN }
    val isInit = state.map { it == MeasurementState.INIT }
    val time = MutableLiveData<Long>()

    val elapsedTime = repository.elapsedTime.map { formatMeasureTime(it) }.asLiveData()

    @MainThread
    fun onStopwatchMainButton() {
        viewModelScope.launch {
            val entity = repository.entity.first()
            when (entity.state) {
                MeasurementState.STOPWATCH_RUN -> {
                    repository.stopStopwatch()
                }
                MeasurementState.INIT, MeasurementState.STOPWATCH_STOP -> {
                    repository.startStopwatch()
                }
                else -> {
                    // nop
                }
            }
        }
    }

    fun onStopwatchReset() {
        viewModelScope.launch {
            repository.resetStopwatch()
        }
    }

    fun onStopwatchFinish() {
        viewModelScope.launch {
            val result = repository.finishStopwatch()
            time.value = result
        }
    }
}
