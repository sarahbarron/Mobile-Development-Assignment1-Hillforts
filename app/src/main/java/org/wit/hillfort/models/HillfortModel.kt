package org.wit.hillfort.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String="",
                         var image: String=""): Parcelable