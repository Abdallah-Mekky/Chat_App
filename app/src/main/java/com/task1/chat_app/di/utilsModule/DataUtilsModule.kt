package com.task1.chat_app.di.utilsModule

import com.task1.domain.model.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object DataUtilsModule {

    @Provides
    fun provideCurrentRoom(): Room {

        return Room()

    }


}