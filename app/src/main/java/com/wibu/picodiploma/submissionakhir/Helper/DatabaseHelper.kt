package com.wibu.picodiploma.submissionakhir.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.TABLE_NAME


internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {

        private const val DATABASE_NAME = "dbfavoritapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_FAVORIT = "CREATE TABLE $TABLE_NAME" +
                " ${DatabaseContract.FavoritColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoritColumns.NAMAE} TEXT PRIMARY NOT NULL," +
                " ${DatabaseContract.FavoritColumns.GAMBARPP} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_FAVORIT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }



}