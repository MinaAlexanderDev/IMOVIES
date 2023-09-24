package com.example.imovies.di

import android.content.Context
import androidx.room.Room
import com.example.imovies.data.dataSource.IMovieDetailsDataSource
import com.example.imovies.data.dataSource.MovieDetailsDataSourceImp
import com.example.imovies.data.domain.IMoviesPagerUseCase
import com.example.imovies.data.domain.MoviesPagerUseCase
import com.example.imovies.data.local.MovieDao
import com.example.imovies.data.local.MovieDatabase
import com.example.imovies.data.remote.MoviesApi
import com.example.imovies.data.repository.IMovieDetailsRepository
import com.example.imovies.data.repository.MovieDetailsRepository
import com.example.imovies.util.Constants.BASE_URL
import com.example.imovies.util.Constants.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun providesTMDBApi(okHttpClient: OkHttpClient): MoviesApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            .create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        synchronized(context) {
            Room.databaseBuilder(
                context, MovieDatabase::class.java, DATABASE_NAME
            ).build()
        }


    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MovieDatabase): MovieDao = moviesDatabase.getMoviesDao()

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class MovieDetailsDataSourceModule {

        @Binds
        abstract fun movieDetailsDataSourceImp(
            movieDetailsDataSourceImp: MovieDetailsDataSourceImp
        ): IMovieDetailsDataSource
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class MovieDetailsRepositoryModule {

        @Binds
        abstract fun movieDetailsDataSourceImp(
            movieDetailsRepository: MovieDetailsRepository
        ): IMovieDetailsRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class MoviesPagerUseCaseModule {

        @Binds
        abstract fun moviesPagerUseCase(
            moviesPagerUseCase: MoviesPagerUseCase
        ): IMoviesPagerUseCase
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher