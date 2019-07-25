package com.elad.meetup.dagger.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import us.egek92.mvvm.persistance.dao.CryptoCurrencyDao
import com.elad.meetup.room.local.Database
import com.elad.meetup.viewmodel.CryptoCurrencyViewModelFactory
import com.elad.meetup.utils.Constants
import com.elad.meetup.utils.Utils
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {
    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE cryptocurrency RENAME TO cryptoCurrencies")
            }
        }
    }

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideCryptocurrenciesDatabase(app: Application): Database = Room.databaseBuilder(
        app,
        Database::class.java, Constants.DATABASE_NAME
    )
        /*.addMigrations(MIGRATION_1_2)*/
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(
        database: Database
    ): CryptoCurrencyDao = database.cryptocurrenciesDao()

    @Provides
    @Singleton
    fun provideCryptocurrenciesViewModelFactory(
        factory: CryptoCurrencyViewModelFactory
    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideUtils(): Utils = Utils(app)
}

