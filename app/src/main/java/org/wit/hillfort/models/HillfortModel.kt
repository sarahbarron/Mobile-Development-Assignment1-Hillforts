package org.wit.hillfort.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String="",
                         var images: ArrayList<String> = ArrayList<String>(),
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var visited: Boolean = false,
                         var notes: String ="",
                         var date: Date,
                         var user: Long = 0
                         ): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

