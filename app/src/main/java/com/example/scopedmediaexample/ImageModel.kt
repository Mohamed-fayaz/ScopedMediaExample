package com.example.mygallery

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageModel (

    var uri: Uri ? = null,
    var title :String? = null,

 ):Parcelable