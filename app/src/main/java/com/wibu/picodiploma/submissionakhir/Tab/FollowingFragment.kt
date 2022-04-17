package com.wibu.picodiploma.submissionakhir.Tab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wibu.picodiploma.submissionakhir.Adapter.ListUNAdapter
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.R
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detailuser.*
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import java.lang.Exception

class FollowingFragment : Fragment() {

    private lateinit var  adapter : ListUNAdapter
    private lateinit var  list : ArrayList<NamaUN>
    val username = arguments?.getString(ARG_FOLLOWING)

    companion object {
        private val ARG_FOLLOWING = "following"
        private val TAG = FollowingFragment::class.java.simpleName
        private lateinit var  followingAdapter : ListUNAdapter

        fun newInstance(username: String): FollowingFragment {
            val ffragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_FOLLOWING, username)
            ffragment.arguments = bundle
            return ffragment

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following,container,false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_following.setHasFixedSize(true)

        list = ArrayList<NamaUN>()
        adapter = ListUNAdapter(list)




        rv_following.layoutManager = LinearLayoutManager(requireContext())
        rv_following.adapter = adapter


        val usernameFromDetail = arguments?.getString(ARG_FOLLOWING)
        Log.d("FollowingFragment",usernameFromDetail.toString())
        getDataFollowing(usernameFromDetail.toString())

    }



    private fun getDataFollowing(username : String) {
        progressSearchFollowing.visibility = View.VISIBLE
        tv_loadfollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val follow = String(responseBody)
                Log.d(TAG,follow)
                val listData : ArrayList<NamaUN> = ArrayList()

                try {
                    val itemsf = JSONArray(follow)

                    for (i in 0 until itemsf.length()) {
                        if (rv_following != null) {
                            progressSearchFollowing.visibility = View.INVISIBLE
                            tv_loadfollowing.visibility = View.INVISIBLE
                            val itemfs = itemsf.getJSONObject(i)
                            val username = itemfs.getString("login")
                            val avatar = itemfs.getString("avatar_url")

                            val followingdata = NamaUN()
                            followingdata.username = username
                            followingdata.profile_picture = avatar

                            listData.add(followingdata)
                        }

                        if (rv_following == null) {
                            progressSearchFollowing.visibility = View.INVISIBLE
                            tv_loadfollowing.visibility = View.INVISIBLE

                        }


                    }
                    list.addAll(listData)
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    e.printStackTrace()

                }


            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {

            }
        })
    }




}