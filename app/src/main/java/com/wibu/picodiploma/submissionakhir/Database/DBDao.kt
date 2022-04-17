package com.wibu.picodiploma.submissionakhir.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

@Dao
interface DBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nameun: NamaUN)

    @Update
    fun update(nameun: NamaUN)

    @Query("DELETE FROM favorit WHERE username = :username")
    fun delete(username:String)

    @Query("SELECT * FROM favorit WHERE username = :username limit 1 ")
    fun get(username:String) : NamaUN

    @Query("SELECT * FROM favorit ORDER BY username ASC")
    fun getAllNotes(): List<NamaUN>
}