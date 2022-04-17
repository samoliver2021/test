package com.wibu.picodiploma.submissionakhir.Database

import android.net.Uri
import android.provider.BaseColumns
import androidx.lifecycle.LiveData
import androidx.room.*
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

object DatabaseContract {
    const val AUTHORITY = "com.wibu.picodiploma.submissionakhir"
    const val SCHEME = "content"

    interface FavoritColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorit"
            const val _ID = "_id"
            const val NAMAE = "username"
            const val GAMBARPP = "avatar_url"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }


    }
}