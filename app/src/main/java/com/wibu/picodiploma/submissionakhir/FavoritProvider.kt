package com.wibu.picodiploma.submissionakhir

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.AUTHORITY
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.CONTENT_URI
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.TABLE_NAME
import com.wibu.picodiploma.submissionakhir.Favorit.FavoritHelper

class FavoritProvider : ContentProvider() {

    companion object {
        private const val FAVORIT = 1
        private const val FAVORIT_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoritHelper : FavoritHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORIT)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORIT_ID)
        }


    }
    override fun onCreate(): Boolean {
        favoritHelper = FavoritHelper.getInstance(context as Context)
        favoritHelper.open()
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORIT -> favoritHelper.queryAll()
            FAVORIT_ID -> favoritHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAVORIT) {
            sUriMatcher.match(uri) -> favoritHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (FAVORIT_ID) {
            sUriMatcher.match(uri) -> favoritHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAVORIT_ID) {
            sUriMatcher.match(uri) -> favoritHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
