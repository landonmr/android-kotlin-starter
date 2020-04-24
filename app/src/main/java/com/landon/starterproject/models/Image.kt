package com.landon.starterproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Image (
    val path: String,
    val extension: String
) : Parcelable