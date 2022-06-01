package com.example.informationdisplayapp

import android.os.Parcel
import android.os.Parcelable

data class item(val image:Int, val name:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<item> {
        override fun createFromParcel(parcel: Parcel): item {
            return item(parcel)
        }

        override fun newArray(size: Int): Array<item?> {
            return arrayOfNulls(size)
        }
    }
}
