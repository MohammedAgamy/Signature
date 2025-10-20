package com.agamy.signature.di

import com.agamy.signature.data.repositoryimpl.DrawRepositoryImpl
import com.agamy.signature.domain.repository.DrawRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Dagger Hilt module to provide application-wide dependencies
// This module is installed in the SingletonComponent
// It provides a singleton instance of DrawRepository
// which is implemented by DrawRepositoryImpl
// This setup allows for dependency injection throughout the app
// ensuring that there is a single instance of DrawRepository used across the application
// making it easier to manage and test
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDrawRepository(): DrawRepository = DrawRepositoryImpl()
}