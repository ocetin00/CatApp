package com.oguzhancetin.core_data.di

import com.oguzhancetin.core_data.repository.CatRepository
import com.oguzhancetin.core_data.repository.CatRepositoryImp
import com.oguzhancetin.core_network.service.CatService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {


    @Binds
    fun bindsCatRepository(
        catRepositoryImp: CatRepositoryImp
    ): CatRepository


}
