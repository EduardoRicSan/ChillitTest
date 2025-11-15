package com.tech.data.di

import com.tech.data.dataSource.auth.AuthRemoteDataSource
import com.tech.data.dataSource.auth.AuthRemoteDataSourceImpl
import com.tech.data.dataSource.task.TaskRemoteDataSource
import com.tech.data.dataSource.task.TaskRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTaskRemoteDataSource(
        impl: TaskRemoteDataSourceImpl
    ): TaskRemoteDataSource

}