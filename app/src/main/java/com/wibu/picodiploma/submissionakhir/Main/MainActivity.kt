package com.wibu.picodiploma.submissionakhir.Main


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wibu.picodiploma.submissionakhir.Adapter.ListUNAdapter
import com.wibu.picodiploma.submissionakhir.Detail.DetailuserActivity
import com.wibu.picodiploma.submissionakhir.Favorit.Favorit
import com.wibu.picodiploma.submissionakhir.R
import com.wibu.picodiploma.submissionakhir.Setting.SettingActivity
import com.wibu.picodiploma.submissionakhir.Switch.MainViewModel
import com.wibu.picodiploma.submissionakhir.Switch.SettingPreferences
import com.wibu.picodiploma.submissionakhir.Switch.ViewModelFactory
import com.wibu.picodiploma.submissionakhir.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_detailuser.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val UN = ArrayList<NamaUN>()
    val ListUNAdapter = ListUNAdapter(UN)

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
        binding.progressSearch.visibility = View.INVISIBLE
        binding.tvLoading.visibility = View.INVISIBLE
        getData("")
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = ListUNAdapter

        ListUNAdapter.setOnItemClickCallback(object : ListUNAdapter.OnItemClickCallback {
            override fun onItemClicked(detailuser: NamaUN) {
                val moveDetail = Intent(this@MainActivity, DetailuserActivity::class.java)
                detailuser.username
                moveDetail.putExtra(DetailuserActivity.EXTRA_DETAIL, detailuser.username)
                startActivity(moveDetail)

            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.searching).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.searching)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(username: String): Boolean {
                binding.progressSearch.visibility = View.VISIBLE
                binding.tvLoading.visibility = View.VISIBLE
                binding.gambarSearch.visibility = View.INVISIBLE
                binding.tvStartsearch.visibility = View.INVISIBLE
                getData(username)
                return true
            }

            override fun onQueryTextChange(change: String): Boolean {

                return false
            }


        })
        0
        return true
    }

    private fun showSelected(detailuser : NamaUN) {

    }

    fun getData(username : String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    UN.clear()
                    binding.progressSearch.visibility = View.INVISIBLE
                    binding.tvLoading.visibility = View.INVISIBLE
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val getuser = NamaUN()
                        getuser.username = username
                        getuser.profile_picture = avatar
                        UN.add(getuser)
                    }

                    ListUNAdapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {


            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }

            R.id.favorit -> {
                val i = Intent(this, Favorit::class.java)
                startActivity(i)
                return true
            }
            else -> return true

        }
        return super.onOptionsItemSelected(item)
    }


}


