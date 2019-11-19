package com.elad.meetup.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(
    tableName = "creditCards"
)

data class CreditCard @JvmOverloads constructor(

    @Json(name = "id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @Json(name = "name")
    @ColumnInfo(name = "name")
    var name: String?,

    @Json(name = "symbol")
    @ColumnInfo(name = "symbol")
    val symbol: String,

    @Json(name = "rank")
    @ColumnInfo(name = "rank")
    val rank: Int,

    @Json(name = "price_usd")
    @ColumnInfo(name = "price_usd")
    val priceUsd: Double?,

    @Json(name = "price_btc")
    @ColumnInfo(name = "price_btc")
    var priceBtc: String?,

    @Json(name = "24h_volume_usd")
    @ColumnInfo(name = "24h_volume_usd")
    val volumeUsd24h: String?,

    @Json(name = "market_cap_usd")
    @ColumnInfo(name = "market_cap_usd")
    val marketCapUsd: String?,

    @Json(name = "available_supply")
    @ColumnInfo(name = "available_supply")
    val availableSupply: String?,

    @Json(name = "total_supply")
    @ColumnInfo(name = "total_supply")
    val totalSupply: String?,

    @Json(name = "max_supply")
    @ColumnInfo(name = "max_supply")
    val maxSupply: String?,

    @Json(name = "percent_change_1h")
    @ColumnInfo(name = "percent_change_1h")
    val percentChange1h: String?,

    @Json(name = "percent_change_24h")
    @ColumnInfo(name = "percent_change_24h")
    val percentChange24h: String?,

    @Json(name = "percent_change_7d")
    @ColumnInfo(name = "percent_change_7d")
    val percentChange7d: String?,

    @Json(name = "last_updated")
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Double
) : Serializable {
    constructor(id: String) : this(id, "", "", 0, 0.0, "", "", "", "", "", "", "", "", "", 0.0)
    companion object {
        const val VISA = "1"
        const val MASTER_CARD = "2"
        const val UP_CARD = "3"
    }

     fun isTypeUpCard(): Boolean {
        return id == UP_CARD
    }

}