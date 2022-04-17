package com.wibu.picodiploma.submissionakhir.Database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wibu.picodiploma.submissionakhir.Repository.UNRepository
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

 class DBMainViewModel : ViewModel(){

    private lateinit var Repository : UNRepository

    fun setRepository(Repository : UNRepository) {
        this.Repository = Repository
    }

    fun getAllUN() : List<NamaUN> = Repository.getAllNotes()

     fun insertUN(un : NamaUN) {
         Repository.insert(un)
     }

     fun deleteUN(username: String) {
         Repository.delete(username)
     }

     fun getUN(username : String) : NamaUN = Repository.get(username)
}