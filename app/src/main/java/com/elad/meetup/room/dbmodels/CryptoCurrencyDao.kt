package us.egek92.mvvm.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import com.elad.meetup.model.CryptoCurrency

@Dao
interface CryptoCurrencyDao {

  @Query("SELECT * FROM cryptoCurrencies ORDER BY rank limit :limit offset :offset")
  suspend fun queryCryptocurrencies(limit: Int, offset: Int): List<CryptoCurrency>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCryptocurrency(cryptoCurrency: CryptoCurrency)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  suspend fun insertAllCryptocurrencies(cryptoCurrencies: List<CryptoCurrency>)
}