package com.example.moviemaster.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val video: Boolean,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overView: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("runtime")
    val runTime: String?,
) : Parcelable {


    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.createIntArray()?.toList(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(popularity)
        parcel.writeDouble(voteCount)
        parcel.writeString(posterPath)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(backdropPath)
        parcel.writeString(title)
        parcel.writeString(originalTitle)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(originalLanguage)
        parcel.writeIntArray(genreIds?.toIntArray())
        parcel.writeDouble(voteAverage)
        parcel.writeString(overView)
        parcel.writeString(releaseDate)
        parcel.writeString(runTime)
    }

    override fun describeContents(): Int {
        return 0
    }


}