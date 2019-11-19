package com.elad.meetup.room.dbmodels

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elad.meetup.model.CreditCard

@Dao
interface CryptoCurrencyDao {

  @Query("SELECT * FROM creditCards ORDER BY rank limit :limit offset :offset")
  suspend fun queryCryptocurrencies(limit: Int, offset: Int): List<CreditCard>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCryptocurrency(creditCard: CreditCard)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  suspend fun insertAllCryptocurrencies(creditCards: List<CreditCard>)
}