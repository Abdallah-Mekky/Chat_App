package com.task1.data.di.roomRepoModule

import com.task1.data.reposImpl.roomRepoImpl.RoomOnlineDataSourceImpl
import com.task1.data.reposImpl.roomRepoImpl.RoomRepoImpl
import com.task1.domain.repos.roomRepo.RoomOnlineDataSource
import com.task1.domain.repos.roomRepo.RoomRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    fun provideRoomOnlineDataSource(): RoomOnlineDataSource {

        return RoomOnlineDataSourceImpl()
    }

    @Provides
    fun provideRoomRepo(roomOnlineDataSource: RoomOnlineDataSource): RoomRepo {

        return RoomRepoImpl(roomOnlineDataSource)
    }
}