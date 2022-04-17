package com.wibu.picodiploma.submissionakhir.Favorit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.TABLE_NAME
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.NAMAE
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion._ID
import com.wibu.picodiploma.submissionakhir.Helper.DatabaseHelper


class FavoritHelper(context: Context) {
    private var dataBaseHelper : DatabaseHelper = DatabaseHelper(context)
    private  var database : SQLiteDatabase = dataBaseHelper.writableDatabase


    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE : FavoritHelper? = null

        fun getInstance(context: Context) : FavoritHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoritHelper(context)
            }

    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLiteException ::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll() : Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$NAMAE = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$NAMAE = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$NAMAE = '$id'", null)
    }
}