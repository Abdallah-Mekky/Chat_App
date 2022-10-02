package com.task1.data.di.userRoomRepoModule

import com.task1.data.reposImpl.userRoomRepo.UserRoomOnlineDataSourceImpl
import com.task1.data.reposImpl.userRoomRepo.UserRoomRepoImpl
import com.task1.domain.repos.userRoomRepo.UserRoomOnlineDataSource
import com.task1.domain.repos.userRoomRepo.UserRoomRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserRoomModule {


    @Provides
    fun provideUserRoomOnlineDataSource(): UserRoomOnlineDataSource {

        return UserRoomOnlineDataSourceImpl()
    }

    @Provides
    fun provideUserRoomRepo(UserRoomOnlineDataSource: UserRoomOnlineDataSource): UserRoomRepo {

        return UserRoomRepoImpl(UserRoomOnlineDataSource)
    }
}