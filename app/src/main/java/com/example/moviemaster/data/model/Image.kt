package com.example.moviemaster.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Image(

    @SerializedName("base_url")
    val baseUrl: String?,
    @SerializedName("file_path")
    val filePath: String?,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String?,
    @SerializedName("backdrop_sizes")
    val backdropSizes: ArrayList<String>?,
    @SerializedName("logo_sizes")
    val logoSizes: ArrayList<String>?,
    @SerializedName("poster_sizes")
    val posterSizes: ArrayList<String>?,
    @SerializedName("profile_sizes")
    val profileSizes: ArrayList<String>?,
    @SerializedName("still_sizes")
    val stillSizes: ArrayList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(baseUrl)
        parcel.writeString(filePath)
        parcel.writeString(secureBaseUrl)
        parcel.writeStringList(backdropSizes)
        parcel.writeStringList(logoSizes)
        parcel.writeStringList(posterSizes)
        parcel.writeStringList(profileSizes)
        parcel.writeStringList(stillSizes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}

