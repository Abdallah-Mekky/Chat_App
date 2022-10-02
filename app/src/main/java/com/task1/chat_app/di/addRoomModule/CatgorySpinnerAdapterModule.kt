package com.task1.chat_app.di.addRoomModule

import com.task1.chat_app.ui.addRoom.CatgorySpinnerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CatgorySpinnerAdapterModule {

    @Provides
    fun provideCatgorySpinnerAdapter(): CatgorySpinnerAdapter {

        return CatgorySpinnerAdapter(null)
    }
}