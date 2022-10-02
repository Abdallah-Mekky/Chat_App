package com.task1.data.di.userFirebaseRepoModule

import com.task1.data.reposImpl.userFirebaseRepoImpl.UserFirebaseOnlineDataSourceImpl
import com.task1.data.reposImpl.userFirebaseRepoImpl.UserFirebaseRepoImpl
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseOnlineDataSource
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserFirebaseModule {


    @Provides
    fun provideUserFirebaseOnlineDataSource(): UserFirebaseOnlineDataSource {

        return UserFirebaseOnlineDataSourceImpl()
    }

    @Provides
    fun provideUserFirebaseRepo(UserFirebaseOnlineDataSource: UserFirebaseOnlineDataSource): UserFirebaseRepo {

        return UserFirebaseRepoImpl(UserFirebaseOnlineDataSource)
    }
}