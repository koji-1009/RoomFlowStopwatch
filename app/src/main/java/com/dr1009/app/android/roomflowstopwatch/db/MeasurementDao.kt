package com.dr1009.app.android.roomflowstopwatch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dr1009.app.android.roomflowstopwatch.entity.MeasurementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {

    @Query("SELECT * FROM measurement WHERE entity_id=0")
    fun findFlow(): Flow<MeasurementEntity?>

    @Query("SELECT * FROM measurement WHERE entity_id=0")
    suspend fun find(): MeasurementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MeasurementEntity)
}