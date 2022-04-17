package com.wibu.picodiploma.submissionakhir.Tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wibu.picodiploma.submissionakhir.Adapter.ListUNAdapter
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.R
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detailuser.*
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_follower.progressSearchFollower
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray


class FollowerFragment : Fragment() {

    private lateinit var  adapter : ListUNAdapter
    private lateinit var  list : ArrayList<NamaUN>
    val username = arguments?.getString(ARG_FOLLOWER)

    companion object {
        private val ARG_FOLLOWER = "follower"
        private val TAG = FollowerFragment::class.java.simpleName
        private lateinit var  followerAdapter : ListUNAdapter
        private lateinit var list : ArrayList<NamaUN>

        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_FOLLOWER, username)
            fragment.arguments = bundle
            return fragment

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_follower.setHasFixedSize(true)

        list = ArrayList()
        adapter = ListUNAdapter(list)

        rv_follower.layoutManager = LinearLayoutManager(requireContext())
        rv_follower.adapter = adapter

        val usernameFromDetail = arguments?.getString(ARG_FOLLOWER)


        Log.d("FollowerFragment", usernameFromDetail.toString())
        getDataFollow(usernameFromDetail.toString())


    }


    private fun getDataFollow(username: String) {
        rv_follower.visibility = View.VISIBLE
        tv_loadfollower.visibility = View.INVISIBLE
        progressSearchFollower.visibility = View.INVISIBLE

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val fresult = String(responseBody)
                Log.d(TAG, fresult)
                val listData: ArrayList<NamaUN> = ArrayList()
                try {

                    val fitem = JSONArray(fresult)

                    for (i in 0 until fitem.length()) {
                        progressSearchFollower.visibility = View.INVISIBLE
                        tv_loadfollower.visibility = View.INVISIBLE
                        rv_follower.visibility = View.VISIBLE
                        val fitems = fitem.getJSONObject(i)
                        val username = fitems.getString("login")
                        val avatar = fitems.getString("avatar_url")

                            val followerdata = NamaUN()
                            followerdata.username = username
                            followerdata.profile_picture = avatar

                            listData.add(followerdata)



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





