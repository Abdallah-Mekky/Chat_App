package com.task1.chat_app.di.chatModule

import com.task1.chat_app.ui.chat.ChatAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ChatAdapterModule {

    @Provides
    fun provideChatAdapter(): ChatAdapter {

        return ChatAdapter()
    }
}