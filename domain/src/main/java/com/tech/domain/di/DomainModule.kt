package com.tech.domain.di

import android.content.Context
import androidx.room.Room
import com.tech.domain.repository.local.AppDatabase
import com.tech.domain.repository.local.TaskDao
import com.tech.domain.repository.local.TaskLocalDataSource
import com.tech.domain.repository.local.TaskLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "tasks_db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()


}

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    @Singleton
    abstract fun bindTaskLocalDataSource(
        impl: TaskLocalDataSourceImpl
    ): TaskLocalDataSource
}