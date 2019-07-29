package com.elad.meetup.room.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elad.meetup.model.CryptoCurrency
import com.elad.meetup.room.dbmodels.CryptoCurrencyDao


@Database(entities = [(CryptoCurrency::class)], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun cryptocurrenciesDao(): CryptoCurrencyDao
}