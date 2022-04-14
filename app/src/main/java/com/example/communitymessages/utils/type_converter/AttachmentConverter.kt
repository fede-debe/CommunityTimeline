package com.example.communitymessages.utils.type_converter

import androidx.room.TypeConverter
import com.example.communitymessages.domain.model.local.Attachment
import com.google.gson.Gson

class AttachmentConverter {
    @TypeConverter
    fun listToJsonString(value: List<Attachment>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) =
        Gson().fromJson(value, Array<Attachment>::class.java).toList()
}
