package com.wibu.picodiploma.submissionakhir

import android.database.Cursor
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

object MappingHelper {

    fun mapCursorToArrayList(favoritCursor: Cursor?): ArrayList<NamaUN> {
        val favoritList = ArrayList<NamaUN>()

        favoritCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.NAMAE))
                val profilepicture = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.GAMBARPP))

                favoritList.add(NamaUN(username, profilepicture))
            }
        }
        return favoritList
    }

}