package com.canberkbbc.kotlin_countries.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@SuppressLint("ParcelCreator")
@Parcelize
data class CountryModel(
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    var capital: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var currency: String?,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    var flag: String?,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    var language: String?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    var region: String?
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}