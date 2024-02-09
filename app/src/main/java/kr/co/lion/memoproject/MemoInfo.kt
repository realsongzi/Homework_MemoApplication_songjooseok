package kr.co.lion.memoproject

import android.os.Parcel
import android.os.Parcelable

data class MemoInfo(var title: String?, var content: String?, val date: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoInfo> {
        override fun createFromParcel(parcel: Parcel): MemoInfo {
            return MemoInfo(parcel)
        }

        override fun newArray(size: Int): Array<MemoInfo?> {
            return arrayOfNulls(size)
        }
    }

}