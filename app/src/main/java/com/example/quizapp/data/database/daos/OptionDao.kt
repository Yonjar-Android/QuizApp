package com.example.quizapp.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.data.database.entities.OptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {

    @Query("SELECT * FROM option WHERE idQuestion = :idQuestion")
    fun getAllOptions(idQuestion: Long): Flow<List<OptionEntity>>

    @Insert
    suspend fun insertOption(option: OptionEntity): Long

    @Update
    suspend fun updateOption(option: OptionEntity)

    @Delete
    suspend fun deleteOption(option: OptionEntity)

}