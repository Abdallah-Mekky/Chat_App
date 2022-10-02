package com.task1.data.di.messageRepoModule

import com.task1.data.reposImpl.messageRepoImpl.MessageOnlineDataSourceImpl
import com.task1.data.reposImpl.messageRepoImpl.MessageRepoImpl
import com.task1.domain.repos.messageRepo.MessageOnlineDataSource
import com.task1.domain.repos.messageRepo.MessageRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MessageModule {

    @Provides
    fun provideMessageOnlineDataSource(): MessageOnlineDataSource {

        return MessageOnlineDataSourceImpl()
    }

    @Provides
    fun provideMessageRepo(
        messageOnlineDataSource: MessageOnlineDataSource
    ): MessageRepo {

        return MessageRepoImpl(messageOnlineDataSource)
    }
}