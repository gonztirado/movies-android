package com.gonztirado.app.features.movies.viewmodel

import android.os.Parcel
import com.gonztirado.app.util.platform.KParcelable
import com.gonztirado.app.util.platform.parcelableCreator

data class MovieView(val id: String, val poster: String, val title: String) : KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(::MovieView)
    }

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
            writeString(poster)
            writeString(title)
        }
    }
}
