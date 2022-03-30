package com.example.communitymessages.domain.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment(
    val id: String,
    val mimeType: String,
    val name: String,
    val size: Int,
    val url: String
) : Parcelable