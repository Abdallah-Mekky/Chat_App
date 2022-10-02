package com.task1.chat_app.di.homeModule

import com.task1.chat_app.ui.home.RoomsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RoomsAdapterModule {

    @Provides
    fun provideRoomAdapter(): RoomsAdapter {

        return RoomsAdapter(null)
    }
}