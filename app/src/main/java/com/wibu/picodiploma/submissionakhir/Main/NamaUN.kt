package com.wibu.picodiploma.submissionakhir.Main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorit", indices = arrayOf(Index(value = arrayOf("username"), unique = true)))

@Parcelize
data class NamaUN(

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "profilepicture")
    var profile_picture: String? = null,

    @ColumnInfo(name = "fullname")
    var fullname: String? = null,

    @ColumnInfo(name = "location")
    var lcoation: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "repos")
    var repos: String? = null,

    @ColumnInfo(name = "followers")
    var followers: String? = null,

    @ColumnInfo(name = "following")
    var following: String? = null,


    ):Parcelable {
  @PrimaryKey (autoGenerate = true)
  var id : Int? = null
    }


