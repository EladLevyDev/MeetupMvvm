
package com.elad.meetup.dagger.module

import com.elad.meetup.repo.network.ApiInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.MoshiConverterFactory
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String) {

  @Provides
  @Singleton
  fun providesOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(
      httpLoggingInterceptor).build()

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { })
    interceptor.level = HttpLoggingInterceptor.Level.BASIC
    return interceptor
  }

  @Provides
  @Singleton
  fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  @Provides
  @Singleton
  fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Builder().client(okHttpClient).baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Provides
  @Singleton
  fun providesApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
      ApiInterface::class.java)
}
