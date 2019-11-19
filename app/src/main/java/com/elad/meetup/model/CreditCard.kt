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

    @Json(name = "preffix")
    @ColumnInfo(name = "preffix")
    var preffix: String?


) : Serializable {

    constructor(id: String) : this(id, "")
    companion object {
        const val VISA = "1"
        const val MASTER_CARD = "2"
        const val UP_CARD = "3"
    }

     fun isTypeUpCard(): Boolean {
        return id == UP_CARD
    }

}
