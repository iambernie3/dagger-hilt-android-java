package com.gte.myjavadaggerhilt.di;

import com.gte.myjavadaggerhilt.data.repository.ApiRepositoryImpl;
import com.gte.myjavadaggerhilt.domain.repository.IApiRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract IApiRepository bindApiRepository(ApiRepositoryImpl repository);
}
