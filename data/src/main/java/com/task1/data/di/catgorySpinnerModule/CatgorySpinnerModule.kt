package com.task1.data.di.catgorySpinnerModule

import com.task1.domain.model.CatgorySpinner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent



@Module
@InstallIn(ViewModelComponent::class)
object CatgorySpinnerModule {

    @Provides
    fun provideCatgorySpinner(): CatgorySpinner {

        return CatgorySpinner()
    }
}