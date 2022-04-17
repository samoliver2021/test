package com.wibu.picodiploma.submissionakhir.Favorit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wibu.picodiploma.submissionakhir.Database.DBMainViewModel
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.MappingHelper
import com.wibu.picodiploma.submissionakhir.R
import com.wibu.picodiploma.submissionakhir.Database.DatabaseContract.FavoritColumns.Companion.CONTENT_URI
import com.wibu.picodiploma.submissionakhir.Detail.DetailuserActivity
import com.wibu.picodiploma.submissionakhir.Repository.UNRepository
import com.wibu.picodiploma.submissionakhir.databinding.ActivityFavoritBinding
import kotlinx.android.synthetic.main.activity_favorit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Favorit : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritBinding

    private lateinit var adapter: FavoritAdapter
    private val favoritun = NamaUN()
    private var position: Int = 0
    private lateinit var data : Intent
    private var favorit: NamaUN? = null
    private var FavoritAnda = false
    private lateinit var uriWithId: Uri
    private lateinit var favorita: Favorit

    companion object {
        private const val EXTRA_STATE = "extra_state"
        private const val EXTRA_FAVORIT = "extra_favorit"
        private const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var viewModel: DBMainViewModel
    private  lateinit var Repository : UNRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit)

        Repository = UNRepository(this.application)
        viewModel = ViewModelProvider(this).get(DBMainViewModel ::class.java)
        viewModel.setRepository(Repository)

        favorit = intent.getParcelableExtra(EXTRA_FAVORIT)

        if (favorit != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)


        } else {
            favorit = NamaUN()

        }



        rv_favorit.layoutManager = LinearLayoutManager(this)
        rv_favorit.setHasFixedSize(true)
        adapter = FavoritAdapter(this)
        rv_favorit.adapter = adapter
        loadData()

    }

    private fun loadData() {
        val ListFav = viewModel.getAllUN()
        adapter.listFavorit = ArrayList(ListFav)

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorit)

    }

    private fun snackbar(message: Int) {
        Snackbar.make(rv_favorit, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {

        super.onResume()
        loadData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            when (requestCode) {

                //Delete
                DetailuserActivity.RESULT_DELETE -> {
                    val position = data.getIntExtra(DetailuserActivity.EXTRA_POSITION, 0)

                    adapter.notifyItemRemoved(position)

                    snackbar(R.string.delete_data)
                }
            }
        }
    }


}
