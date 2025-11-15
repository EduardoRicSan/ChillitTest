package com.tech.domain.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.domain.model.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
