package com.task1.chat_app.di.chatModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*


@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    fun provideTimeOfMessage():Long{

        return Date().time
    }
}