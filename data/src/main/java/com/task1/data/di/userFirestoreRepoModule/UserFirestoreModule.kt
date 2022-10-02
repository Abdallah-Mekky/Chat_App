package com.task1.data.di.userFirestoreRepoModule

import com.task1.data.reposImpl.userFirestoreRepoImpl.UserFirestoreOnlineDataSourceImpl
import com.task1.data.reposImpl.userFirestoreRepoImpl.UserFirestoreRepoImpl
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreOnlineDataSource
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserFirestoreModule {


    @Provides
    fun provideUserFirestoreOnlineDataSource(): UserFirestoreOnlineDataSource {

        return UserFirestoreOnlineDataSourceImpl()
    }

    @Provides
    fun provideUserFirestoreRepo(UserFirestoreOnlineDataSource: UserFirestoreOnlineDataSource): UserFirestoreRepo {

        return UserFirestoreRepoImpl(UserFirestoreOnlineDataSource)
    }
}