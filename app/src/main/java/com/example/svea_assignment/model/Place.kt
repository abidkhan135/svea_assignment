package com.example.svea_assignment.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("alias") val alias : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("longitude") val longitude : Double?,
    @SerializedName("latitude") val latitude : Double?,
    @SerializedName("description") val description : String?,
    @SerializedName("icon") val icon : String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alias)
        parcel.writeString(name)
        parcel.writeValue(longitude)
        parcel.writeValue(latitude)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }
}