package com.example.mybooks.di

import com.example.mybooks.data.repository.RealtimeDatabaseRepositoryImpl
import com.example.mybooks.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRealtimeDatabaseRepositoryImpl(impl: RealtimeDatabaseRepositoryImpl): Repository
}