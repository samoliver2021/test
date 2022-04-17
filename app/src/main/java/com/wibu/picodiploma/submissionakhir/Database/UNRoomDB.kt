package com.wibu.picodiploma.submissionakhir.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

@Database(entities = [NamaUN::class],version = 1, exportSchema = false)
abstract class UNRoomDB : RoomDatabase(){
    abstract fun unDao() : DBDao

    companion object {
        @Volatile
        private var INSTANCE: UNRoomDB? = null

        @JvmStatic

        fun getDatabase(context: Context): UNRoomDB {
            if (INSTANCE == null) {
                synchronized(UNRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UNRoomDB::class.java, "note_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as UNRoomDB
        }
    }
}