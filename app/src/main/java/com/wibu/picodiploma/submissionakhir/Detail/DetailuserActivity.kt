package com.wibu.picodiploma.submissionakhir.Detail

import android.content.Intent
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wibu.picodiploma.submissionakhir.*
import com.wibu.picodiploma.submissionakhir.Adapter.SectionsPagerAdapter
import com.wibu.picodiploma.submissionakhir.Database.DBMainViewModel
import com.wibu.picodiploma.submissionakhir.Favorit.Favorit
import com.wibu.picodiploma.submissionakhir.Favorit.FavoritAdapter
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.Repository.UNRepository
import com.wibu.picodiploma.submissionakhir.databinding.ActivityDetailuserBinding
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detailuser.*
import kotlinx.android.synthetic.main.activity_favorit.*
import kotlinx.android.synthetic.main.activity_setting.view.*
import kotlinx.android.synthetic.main.list_un.*
import kotlinx.android.synthetic.main.list_un.view.*
import kotlinx.android.synthetic.main.templete_favorit.*
import kotlinx.android.synthetic.main.templete_favorit.view.*
import org.json.JSONObject

class DetailuserActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityDetailuserBinding
    private var FavoritAnda = false
    private lateinit var adapter: FavoritAdapter

    /*private lateinit var favoritHelper: FavoritHelper*/
    private var favorit: NamaUN? = null
    private lateinit var gambarpp: String
    private lateinit var uriWithId: Uri
    private var position: Int = 0
    private val FavoritList = "FavoritList"
    private val detailuser = NamaUN()
    private lateinit var viewModel: DBMainViewModel
    private lateinit var Repository: UNRepository

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val REQUEST_UPDATE = 200
        const val EXTRA_STATE = "EXTRA_STATE"
        const val EXTRA_FAVORIT = "FavoritList"
        const val EXTRA_POSITION = "PositionList"
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Repository = UNRepository(this.application)
        viewModel = ViewModelProvider(this).get(DBMainViewModel::class.java)
        viewModel.setRepository(Repository)


        favorit = intent.getParcelableExtra(EXTRA_FAVORIT)


        val usernameFromMain = intent.getStringExtra(EXTRA_DETAIL)

        //Hanya loading yg muncul
        binding.tvLoading.visibility = View.VISIBLE
        binding.progressSearch.visibility = View.VISIBLE
        binding.textRepost.visibility = View.INVISIBLE
        binding.textFollowers.visibility = View.INVISIBLE
        binding.textFollowing.visibility = View.INVISIBLE
        binding.textinfo.visibility = View.INVISIBLE
        binding.line2.visibility = View.INVISIBLE
        binding.gambarlocation.visibility = View.INVISIBLE
        binding.gambarperusahaan.visibility = View.INVISIBLE
        binding.gambarprofile.visibility = View.INVISIBLE
        binding.tabs.visibility = View.INVISIBLE
        binding.viewPager.visibility = View.INVISIBLE


        loadDetail(usernameFromMain.toString())

        //Untuk follower dan following
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setUsername(usernameFromMain.toString())
        view_pager.adapter = sectionsPagerAdapter

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
        fab_favor.setOnClickListener(this)


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorit)
    }

    //Mengirimkan data ke detail
    private fun loadDetail(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")


        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.tvLoading.visibility = View.INVISIBLE
                binding.progressSearch.visibility = View.INVISIBLE
                binding.textRepost.visibility = View.VISIBLE
                binding.textFollowers.visibility = View.VISIBLE
                binding.textFollowing.visibility = View.VISIBLE
                binding.line2.visibility = View.VISIBLE
                binding.textinfo.visibility = View.VISIBLE
                binding.gambarlocation.visibility = View.VISIBLE
                binding.gambarperusahaan.visibility = View.VISIBLE
                binding.gambarprofile.visibility = View.VISIBLE
                binding.tabs.visibility = View.VISIBLE
                binding.viewPager.visibility = View.VISIBLE

                val result = String(responseBody)
                Log.d("Detailuser", result)
                try {
                    val item = JSONObject(result)
                    val username = item.getString("login")
                    val avatar = item.getString("avatar_url")
                    val name = item.getString("name")
                    val company = item.getString("company")
                    val location = item.getString("location")
                    val repos = item.getString("public_repos")
                    val follower = item.getString("followers")
                    val following = item.getString("following")

                    detailuser.username = username
                    detailuser.profile_picture = avatar
                    detailuser.fullname = name
                    detailuser.company = company
                    detailuser.lcoation = location
                    detailuser.repos = repos
                    detailuser.followers = follower
                    detailuser.following = following
                    loadFavorit(username)
                    tv_repost.text = detailuser.repos
                    tv_follow.text = detailuser.followers
                    tv_following.text = detailuser.following

                    tv_une.text = detailuser.username
                    Glide.with(this@DetailuserActivity)
                        .load(detailuser.profile_picture)
                        .apply(RequestOptions().override(90, 90))
                        .into(pp)

                    if (name != "null") {
                        tv_fn.text = detailuser.fullname

                    } else {

                        tv_fn.text = "No data"
                    }

                    if (location != "null") {
                        tv_location.text = detailuser.lcoation
                    } else {
                        tv_location.text = "No data"
                    }


                    if (company != "null") {
                        tv_company.text = detailuser.company
                    } else {
                        tv_company.text = "No data"
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int, headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {

            }

        })


    }

    //Mengirimkan data ke favorit
    private fun loadFavorit(username: String) {


        val data: NamaUN? = viewModel.getUN(username)
        if (data != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            FavoritAnda = true
            val checked: Int = R.drawable.favor
            fab_favor.setImageResource(checked)

        } else {
            val unchecked: Int = R.drawable.favoritkosong
            fab_favor.setImageResource(unchecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    //State hati
    override fun onClick(view: View) {
        val yourFavorit: Int = R.drawable.favor
        val notyourFavorit: Int = R.drawable.favoritkosong

        if (view.id == R.id.fab_favor) {
            if (FavoritAnda) {
                viewModel.deleteUN(detailuser.username ?: "")
                Toast.makeText(this, "Hapus dari favorit", Toast.LENGTH_SHORT).show()
                fab_favor.setImageResource(notyourFavorit)
                FavoritAnda = false
                finish()
            } else {
                Toast.makeText(this, "Tambah", Toast.LENGTH_SHORT).show()
                fab_favor.setImageResource(yourFavorit)
                viewModel.insertUN(detailuser)

                val intent = Intent(this@DetailuserActivity, Favorit::class.java)
                intent.putExtra(EXTRA_FAVORIT, favorit)
                intent.putExtra(EXTRA_POSITION, position)


            }


        }

    }
}