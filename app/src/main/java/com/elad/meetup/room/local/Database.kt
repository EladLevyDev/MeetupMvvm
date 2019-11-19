package com.elad.meetup.room.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elad.meetup.model.CreditCard
import com.elad.meetup.room.dbmodels.CreditCardDao


@Database(entities = [(CreditCard::class)], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun cryptocurrenciesDao(): CreditCardDao
}