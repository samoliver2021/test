package com.wibu.picodiploma.submissionakhir.Helper

import androidx.recyclerview.widget.DiffUtil
import com.wibu.picodiploma.submissionakhir.Main.NamaUN

class DBDiffCallback(private val mDBOld: List<NamaUN>, private val mDBNew: List<NamaUN>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mDBOld.size
    }
    override fun getNewListSize(): Int {
        return mDBNew.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mDBOld[oldItemPosition].username == mDBOld[newItemPosition].username
    }
    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldDB = mDBOld[oldPosition]
        val newDB = mDBOld[newPosition]
        return oldDB.username  == newDB.username && oldDB.profile_picture == newDB.profile_picture
    }
}