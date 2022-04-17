package com.wibu.picodiploma.submissionakhir.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.wibu.picodiploma.submissionakhir.Database.DBDao
import com.wibu.picodiploma.submissionakhir.Database.UNRoomDB
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UNRepository(application: Application) {
    private val mNameun : DBDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val un = UNRoomDB.getDatabase(application)
        mNameun = un.unDao()
    }

    fun getAllNotes(): List<NamaUN> = mNameun.getAllNotes()

    fun insert(nameun: NamaUN) {
        executorService.execute { mNameun.insert(nameun) }
    }
    fun delete(username: String) {
        executorService.execute { mNameun.delete(username) }
    }
    fun update(nameun: NamaUN) {
        executorService.execute { mNameun.update(nameun) }
    }

    fun get(username : String) : NamaUN = mNameun.get(username)



}